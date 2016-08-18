(ns app.main
  (:require [app.components.page :as app.page]
            [core.config :as config]
            [core.reload :as reload]
            [reagent.core :as reagent]))

(def container-style
  {:position "fixed"
   :top 0
   :bottom 0
   :left 0
   :right 0
   :overflow "auto"
   :background-color "white"})

(defn render []
  (set! js/container.style nil)
  (js/Object.assign js/container.style (clj->js container-style))
  (reagent/render [app.page/component] js/container))

(defn init []
  (when-not config/production
    (enable-console-print!)
    (reload/add-handler #'render))
  (render))
