(ns app.main
  (:require [app.component :as app]
            [app.config :as config]
            [reagent.core :as reagent]))

(defn render []
  (reagent/render [app/page] js/container))

(defn init []
  (set! js/container.style.display nil)
  (when-not config/production
    (enable-console-print!)
    (config/add-reload-handler #'render))
  (render))
