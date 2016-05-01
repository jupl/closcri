(ns app.main
  (:require [app.config :as config]))

(defonce load-time (.toLocaleString (js/Date.)))

(defn init []
  (set! js/container.style.display nil)
  (set! js/container.style.backgroundColor "gainsboro")
  (let [update-time (.toLocaleString (js/Date.))
        message (if config/production
                  (str "Loaded at " load-time ".")
                  (str "Loaded at " load-time ". Updated at " update-time))]
    (set! js/container.innerHTML message)))
