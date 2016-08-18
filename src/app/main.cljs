(ns app.main
  (:require [core.config :as config]
            [core.reload :as reload]))

(def container-style
  {:position "fixed"
   :top 0
   :bottom 0
   :left 0
   :right 0
   :overflow "auto"})

(defn render []
  (set! js/container.style nil)
  (js/Object.assign js/container.style (clj->js container-style))
  (set! js/container.innerHTML "Hello world"))

(defn init []
  (set! js/container.style.display nil)
  (when-not config/production
    (enable-console-print!)
    (reload/add-handler #'render))
  (render))
