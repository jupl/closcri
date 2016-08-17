(ns app.main
  (:require [app.component :as app]
            [core.config :as config]
            [core.db :as db]
            [core.reload :as reload]
            [reagent.core :as reagent]))

(defn render []
  (reagent/render [app/page] js/container))

(defn init []
  (set! js/container.style.display nil)
  (when-not config/production
    (enable-console-print!)
    (reload/add-handler #'render))
  (db/init!)
  (render))
