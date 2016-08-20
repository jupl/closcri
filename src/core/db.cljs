(ns core.db
  (:require [core.config :as config]
            [re-frame.core :refer [dispatch dispatch-sync]]
            [re-frame.handlers :refer [register-base]]
            [re-frame.middleware :refer [debug path pure trim-v undoable]]))

;; Set up undo middleware unless in production
(defonce undo-middleware
  (if (identical? config/production true)
    nil
    (undoable)))

(def middleware-before
  "Required middlewares that must come first."
  (if (identical? config/production true)
    [pure trim-v]
    [pure debug trim-v]))

(def middleware-after
  "Required middlewares that must come last."
  (if (identical? config/production true)
    []
    [undo-middleware]))

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
    (dispatch-sync action)))

(defn- build-middlewares
  "Construct middleware with an optional path for re-frame's path middleware."
  [p]
  (apply comp (into middleware-before (if (nil? p)
                                        middleware-after
                                        (into [(path p)] middleware-after)))))

(defn register-handler
  "Custom re-frame handler register."
  ([id handler] (register-handler id nil handler))
  ([id p handler]
   (register-base id (build-middlewares p) handler)))
