(ns app.config)

(goog-define production false)

(def osx (= js/process.platform "darwin"))
