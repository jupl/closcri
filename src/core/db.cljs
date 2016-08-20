(ns core.db
  (:require [re-frame.core :refer [dispatch dispatch-sync]]
            [re-frame.handlers :refer [register-base]]
            [re-frame.middleware :refer [debug pure trim-v undoable]]))

;; Set up undo middleware
(defonce undo-middleware (undoable))

(def init-actions
  "Set of actions that will be called when application loads."
  (atom #{}))

(def middleware
  "Custom middleware used when registering handlers."
  (comp pure debug trim-v undo-middleware))

(defn add-init-dispatch!
  "Add action to get called when application loads."
  [action]
  (swap! init-actions conj action))

(defn init!
  "Initialize app-db by calling on actions that need to be initialized."
  []
  (doseq [action @init-actions]
    (dispatch-sync action)))

(defn register-handler
  "Custom re-frame handler register."
  [id handler]
  (register-base id middleware handler))
