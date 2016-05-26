(ns app.main
  (:require [core.config :as config]
            [core.reload :as reload]))

(defn render []
  (set! js/container.style.backgroundColor "gainsboro")
  (set! js/container.innerHTML "Hello world"))

(defn init []
  (set! js/container.style.display nil)
  (when-not config/production
    (enable-console-print!)
    (reload/add-handler #'render))
  (render))
