(ns awsvault.impl.select
  "select one or more aws profiles, filtered through fzf"
  (:require [fzf.core :refer [fzf]]))

(defn select-profile [profiles]
  (or (fzf profiles)
      (throw (ex-info "No profile selected." {:babashka/exit 1}))))
