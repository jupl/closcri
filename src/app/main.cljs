(ns app.main
  (:require [app.components.page :as app.page]
            [core.config :as config]
            [core.reload :as reload]
            [reagent.core :as reagent]))

(defn render []
  (reagent/render [app.page/component] js/container))

(defn init []
  (set! js/container.style.display nil)
  (when-not config/production
    (enable-console-print!)
    (reload/add-handler #'render))
  (render))
