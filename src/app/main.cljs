(ns app.main
  (:require [app.config :as config]))

(defn render []
  (set! js/container.style.backgroundColor "gainsboro")
  (set! js/container.innerHTML "Hello world"))

(defn init []
  (when-not config/production
    (enable-console-print!)
    (config/add-reload-handler #'render))
  (set! js/container.style.display nil)
  (render))
