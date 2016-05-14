(ns app.config)

(goog-define production false)

(def osx (= js/process.platform "darwin"))

(when-not production
  (enable-console-print!))
