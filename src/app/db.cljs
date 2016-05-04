(ns app.db
  (:require [app.config :as config]
            [re-frame.core :refer [dispatch dispatch-sync]]
            [re-frame.handlers :refer [register-base]]
            [re-frame.middleware :refer [debug pure trim-v undoable]]))

(def init-actions (atom #{}))

(def middleware (if config/production
                  (comp pure trim-v)
                  (do
                    (defonce undo-middleware (undoable))
                    (def undo #(dispatch [:undo]))
                    (def redo #(dispatch [:redo]))
                    (comp pure debug trim-v undo-middleware))))

(defn add-init-dispatch! [action]
  (swap! init-actions conj action))

(defn init! []
  (doseq [action @init-actions]
    (dispatch-sync action)))

(defn register-handler [id handler]
  (register-base id middleware handler))
