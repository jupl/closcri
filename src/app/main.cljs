(ns app.main
  (:require [app.config :as config]))

(defonce load-time (.toLocaleString (js/Date.)))

(defn render []
  (set! js/container.style.backgroundColor "gainsboro")
  (let [update-time (.toLocaleString (js/Date.))
        message (if config/production
                  (str "Loaded at " load-time ".")
                  (str "Loaded at " load-time ". "
                       "Updated at " update-time "."))]
    (set! js/container.innerHTML message)))

(defn init []
  (when config/production
    (enable-console-print!))
  (set! js/container.style.display nil)
  (render))

(defn reload []
  (render))
