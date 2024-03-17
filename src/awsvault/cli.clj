(ns awsvault.cli
  (:require [awsvault.impl.login :refer [login-profile]]
            [awsvault.impl.list :refer [list-profiles]]
            [awsvault.impl.select :refer [select-profile]]
            [awsvault.impl.exec :refer [exec-profile]]))

(defn- die [msg]
  (throw (ex-info msg {:babashka/exit 1})))

(defn- choose-profile [opts]
  (if-let [profiles (list-profiles opts)]
    (if-let [profile (select-profile profiles)]
      profile
      (die "No profile selected."))
    (die "No profiles found.")))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
#_{:clj-kondo/ignore [:redefined-var]}
(defn list [opts]
  (run! println (list-profiles opts)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn select [opts]
  (let [profile (choose-profile opts)]
    (println profile)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn login [{:keys [profile] :as opts}]
  (if profile
    (login-profile profile)
    (let [profile (choose-profile opts)]
      (login-profile profile))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn exec [{:keys [p profile] :as opts}]
  (let [profile (or p profile)]
    (if profile
      (exec-profile profile)
      (let [profile (choose-profile opts)]
        (exec-profile profile)))))
