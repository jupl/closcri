(ns app.config)

(goog-define production false)

(when-not production
  (defonce reload-handlers (atom #{}))
  (defn add-reload-handler [reload-handler]
    (swap! reload-handlers conj reload-handler))
  (defn on-reload []
    (doseq [reload-handler @reload-handlers]
      (try
        (reload-handler)
        (catch js/Error e)))))
