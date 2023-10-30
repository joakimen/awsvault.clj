(ns awsvault.impl.list
  (:require [babashka.fs :as fs]))

(defn list-profiles []
  (let [aws-config-file (str (fs/path (fs/home) ".aws" "config"))
        aws-config (fs/read-all-lines aws-config-file)
        pat #"\[profile ([a-zA-Z0-9\-]+)"]
    (->> aws-config
         (map #(second (re-find pat %)))
         (filter seq))))
