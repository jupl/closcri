(ns core.config)

(goog-define production false)

(def base-url (if production
                (str "file://" js/__dirname "/")
                (let [os (js/require "os")]
                  (str "http://" (.hostname os) ":3000/"))))

(def osx (= js/process.platform "darwin"))
