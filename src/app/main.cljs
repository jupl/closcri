(ns app.main
  (:require [app.component :as app]
            [app.config :as config]
            [reagent.core :refer [render]]))

(defn init []
  (when config/production
    (enable-console-print!))
  (set! js/container.style.display nil)
  (render [#'app/page] js/container))
