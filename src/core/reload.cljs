(ns core.reload)

(defonce handlers (atom #{}))

(defn add-handler [handler]
  (swap! handlers conj handler))

(defn- handle []
  (doseq [handler @handlers]
    (try (handler) (catch js/Error e))))
