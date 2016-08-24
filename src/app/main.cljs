(ns app.main
  (:require [app.components.page :as app.page]
            [core.config :as config]
            [core.db :as db]
            [core.db.devtools :as db-devtools]
            [core.reload :as reload]
            [reagent.core :as reagent]))

(def container-style
  "Style attributes applied to the CLJS application container"
  {:position "fixed"
   :top 0
   :bottom 0
   :left 0
   :right 0
   :overflow "auto"})

(defn render
  "Render the application."
  []
  (set! js/container.style nil)
  (js/Object.assign js/container.style (clj->js container-style))
  (reagent/render [app.page/component] js/container))

(defn init
  "Configure and bootstrap the application."
  []
  (when (identical? config/production false)
    (enable-console-print!)
    (db-devtools/init!)
    (reload/add-handler #'render))
  (db/init!)
  (render))
