(ns app.main
  (:require [core.config :as config]
            [core.reload :as reload]))

(def container-style
  "Style attributes applied to the CLJS application container"
  {:position "fixed"
   :top 0
   :bottom 0
   :left 0
   :right 0
   :overflow "auto"
   :background-color "white"})

(defn render
  "Render the application."
  []
  (set! js/container.style.cssText nil)
  (doseq [[key val] container-style]
    (aset js/container.style (clj->js key) (clj->js val)))
  (set! js/container.innerHTML "Hello world"))

(defn init
  "Configure and bootstrap the application."
  []
  (when (identical? config/production false)
    (enable-console-print!)
    (reload/add-handler #'render))
  (render))
