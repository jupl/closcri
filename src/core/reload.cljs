(ns core.reload)

(defonce handlers
  "Set of handlers called when a hot reload occurs in a dev environment."
  (atom #{}))

(defn add-handler
  "Add a callback function to reload handlers."
  [handler]
  (swap! handlers conj handler))

(defn- handle
  "Invoke all registered reload handlers."
  []
  (doseq [handler @handlers]
    (js/setTimeout #(handler) 0)))
