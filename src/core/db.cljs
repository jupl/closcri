(ns core.db
  (:require [core.config :as config]
            [core.db.devtools :as db-devtools]
            [re-frame.core :as re-frame]
            [re-frame.std-interceptors :refer [debug trim-v]]))

(def post-interceptors
  "Required interceptors that must come last."
  (if (or (identical? config/production true) db-devtools/available)
    [trim-v]
    [debug trim-v]))

(def pre-interceptors
  "Required interceptors that must come first."
  (if (or (identical? config/production true) (not db-devtools/available))
    []
    [(db-devtools/make-interceptor)]))

(def init-actions
  "Set of actions that will be called when application loads."
  (atom #{}))

(defn add-init-dispatch!
  "Add action to get called when application loads."
  [action]
  (swap! init-actions conj action))

(defn reg-event-db
  "Custom re-frame handler register."
  ([id handler]
   (reg-event-db id [] handler))
  ([id interceptors handler]
   (let [interceptors (->> post-interceptors
                           (into interceptors)
                           (into pre-interceptors))]
     (re-frame/reg-event-db id interceptors handler))))

(defn init!
  "Initialize app-db by calling on actions that need to be initialized."
  []
  (doseq [action @init-actions]
    (re-frame/dispatch-sync action))

  ;; After actions are fired, fire an init event for DevTools
  (when (and (identical? config/production false) db-devtools/available)
    (reg-event-db :devtools-init identity)
    (re-frame/dispatch-sync [:devtools-init])))
