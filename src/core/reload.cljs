(ns core.reload)

(defonce handlers (atom #{}))

(defn add-handler [handler]
  (swap! handlers conj handler))

(defn- handle []
  (doseq [handler @handlers]
    (js/setTimeout #(handler) 0)))
