(ns app.main
  (:require [core.config :as config]
            [core.reload :as reload]))

(def container-style
  "Style attributes applied to the CLJS application container"
  {:position "fixed"
   :top 0
   :bottom 0
   :left 0
   :right 0
   :overflow "auto"
   :background-color "white"})

(defn render
  "Render the application."
  []
  (set! js/container.style nil)
  (js/Object.assign js/container.style (clj->js container-style))
  (set! js/container.innerHTML "Hello world"))

(defn init
  "Configure and bootstrap the application."
  []
  (when-not config/production
    (enable-console-print!)
    (reload/add-handler #'render))
  (render))
