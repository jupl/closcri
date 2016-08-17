(ns core.db
  (:require [re-frame.core :refer [dispatch dispatch-sync]]
            [re-frame.handlers :refer [register-base]]
            [re-frame.middleware :refer [debug pure trim-v undoable]]))

(defonce undo-middleware (undoable))

(def init-actions (atom #{}))

(def middleware (comp pure debug trim-v undo-middleware))

(defn add-init-dispatch! [action]
  (swap! init-actions conj action))

(defn init! []
  (doseq [action @init-actions]
    (dispatch-sync action)))

(defn register-handler [id handler]
  (register-base id middleware handler))
