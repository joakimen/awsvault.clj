(ns awsvault.cli
  (:require [awsvault.impl.login :refer [login-profile]]
            [awsvault.impl.list :refer [list-profiles]]
            [awsvault.impl.select :refer [select-profile]]
            [awsvault.impl.exec :refer [exec-profile]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
#_{:clj-kondo/ignore [:redefined-var]}
(defn list [opts]
  (run! println (list-profiles opts)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn select [opts]
  (-> opts
      list-profiles
      select-profile
      println))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn login [{:keys [profile] :as opts}]
  (if profile
    (login-profile profile)
    (-> opts
        list-profiles
        select-profile
        login-profile)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn exec [{:keys [profile] :as opts}]
  (if profile
    (exec-profile profile)
    (-> opts
        list-profiles
        select-profile
        exec-profile)))
