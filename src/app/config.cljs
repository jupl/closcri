(ns app.config)

(goog-define production false)

(def osx (= js/process.platform "darwin"))

(def base-url
  (if production
    (str "file://" js/__dirname "/")
    (let [os (js/require "os")]
      (str "http://" (.hostname os) ":3000/"))))

(when-not production
  (enable-console-print!))
