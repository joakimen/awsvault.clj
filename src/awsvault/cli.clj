(ns awsvault.cli
  (:require [clojure.string :as str]
            [awsvault.impl.login :refer [login-profile]]
            [awsvault.impl.list :refer [list-profiles]]
            [awsvault.impl.select :refer [select-profile]]
            [awsvault.impl.exec :refer [exec-profile]]))

(defn- list-profiles-filtered [opts]
  (let [{:keys [pattern]} opts]
    (cond->> (list-profiles)
      pattern (filter #(str/includes? % pattern)))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn select [opts]
  (-> (list-profiles-filtered opts)
      select-profile
      println))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
#_{:clj-kondo/ignore [:redefined-var]}
(defn list [opts]
  (doseq [profile (list-profiles-filtered opts)]
    (println profile)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn login [opts]
  (let [{:keys [profile]} opts]
    (if profile
      (login-profile profile)
      (-> (list-profiles-filtered opts)
          select-profile
          login-profile))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn exec [opts]
  (let [{:keys [profile]} opts]
    (if profile
      (exec-profile profile)
      (-> (list-profiles-filtered opts)
          select-profile
          exec-profile))))
