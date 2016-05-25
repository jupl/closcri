(ns app.config)

(goog-define production false)

(def base-url (if production
                (str "file://" js/__dirname "/")
                (let [os (js/require "os")]
                  (str "http://" (.hostname os) ":3000/"))))

(def osx (= js/process.platform "darwin"))

(when-not production
  (defonce reload-handlers (atom #{}))
  (defn add-reload-handler [reload-handler]
    (swap! reload-handlers conj reload-handler))
  (defn on-reload []
    (doseq [reload-handler @reload-handlers]
      (try
        (reload-handler)
        (catch js/Error e)))))
