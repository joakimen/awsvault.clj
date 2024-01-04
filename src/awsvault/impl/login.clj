(ns awsvault.impl.login
  "open an isolated chrome instance with the specified aws-profile"
  (:require [awsvault.config :refer [get-property]]
            [babashka.fs :as fs]
            [babashka.process :as p]
            [clojure.string :as str]))

(defn- sh [cmd]
  (let [{:keys [out err exit]} (apply p/sh cmd)]
    (if (zero? exit)
      (str/trim out)
      (throw (ex-info (str/trim err) {:babashka/exit exit})))))

(defn- get-data-dir [profile]
  (str (fs/path (fs/xdg-data-home) "aws_chrome" profile)))

(defn- get-cache-dir [profile]
  (str (fs/path (fs/xdg-cache-home) "aws_chrome" profile)))

(defn- create-login-link [profile]
  (sh ["aws-vault" "login" profile "--stdout" "--prompt" "osascript"]))

(defn- err-exit [msg]
  (throw (ex-info msg {:babashka/exit 1})))

(defn- create-chrome-command [chrome-path profile]
  (let [user-data-dir (get-data-dir profile)
        disk-cache-dir (get-cache-dir profile)
        aws-login-url (create-login-link profile)]
    [chrome-path
     "--no-first-run"
     "--start-maximized"
     (str "--user-data-dir=" user-data-dir)
     (str "--disk-cache-dir=" disk-cache-dir)
     aws-login-url]))

(defn login-profile [profile]

  (let [chrome-path  (get-property :google-chrome-path)]

    (when-not (fs/executable? chrome-path)
      (err-exit (str "couldn't find chrome executable at expected path: " chrome-path)))

    (when-not (fs/which "aws-vault")
      (err-exit "aws-vault is not installed"))

    (let [chrome-command (create-chrome-command chrome-path profile)]
      (apply p/process chrome-command))))
