(ns app.main
  (:require [app.component :as app]
            [app.config :as config]
            [app.db :as db]
            [reagent.core :refer [render]]))

(defn init []
  (db/init!)
  (set! js/container.style.display nil)
  (render [#'app/page] js/container))
