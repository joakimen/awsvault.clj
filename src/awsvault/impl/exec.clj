(ns awsvault.impl.exec
  (:require [babashka.process :as p]))

(defn exec-profile [profile]
  (p/shell "aws-vault" "exec" profile))
