(ns app.main
  (:require [app.component :as app]
            [app.config :as config]
            [reagent.core :as reagent]))

(defn render []
  (reagent/render [app/page] js/container))

(defn init []
  (when-not config/production
    (enable-console-print!)
    (config/add-reload-handler #'render))
  (set! js/container.style.display nil)
  (render))
