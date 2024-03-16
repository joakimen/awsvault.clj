(ns awsvault.impl.list
  (:require [babashka.fs :as fs]
            [clojure.string :as str]))

(defn read-aws-config []
  (fs/read-all-lines (fs/path (fs/home) ".aws" "config")))

(defn parse-aws-config [config]
  (let [pat #"\[profile ([a-zA-Z0-9\-]+)"]
    (->> (map #(second (re-find pat %)) config)
         (filter seq))))

(defn profile-contains-patterns? [profile patterns]
  (some #(when (str/includes? profile %) profile) (str/split patterns #",")))

(defn list-profiles [{:keys [pattern]}]
  (let [config (read-aws-config)
        profiles (parse-aws-config config)]
    (when-let [profiles (cond->> profiles
                          pattern (filter #(profile-contains-patterns? % pattern)))]
      (if (seq profiles)
        profiles
        (throw (ex-info "No profiles found" {:babashka/exit 1}))))))
