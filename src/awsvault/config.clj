(ns awsvault.config)

(def config {:google-chrome-path "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"})

(defn get-property [property]
  (or (get config property)
      (throw (ex-info (str "couldn't find property: " property) {:babashka/exit 1}))))
