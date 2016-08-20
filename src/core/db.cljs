(ns core.db
  (:require [core.config :as config]
            [re-frame.core :as re-frame]
            [re-frame.std-interceptors :refer [debug trim-v]]))

(def post-middlewares
  "Required middlewares that must come last."
  (if (identical? config/production true)
    [trim-v]
    [debug trim-v]))

(def init-actions
  "Set of actions that will be called when application loads."
  (atom #{}))

(defn add-init-dispatch!
  "Add action to get called when application loads."
  [action]
  (swap! init-actions conj action))

(defn init!
  "Initialize app-db by calling on actions that need to be initialized."
  []
  (doseq [action @init-actions]
    (re-frame/dispatch-sync action)))

(defn reg-event-db
  "Custom re-frame handler register."
  ([id handler]
   (reg-event-db id [] handler))
  ([id middlewares handler]
   (re-frame/reg-event-db id (into middlewares post-middlewares) handler)))
