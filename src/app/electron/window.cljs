(ns app.electron.window
  (:require [app.config :as config]
            [camel-snake-kebab.core :refer [->camelCaseString]]
            [camel-snake-kebab.extras :refer [transform-keys]]))

(def browser-window (-> "electron" js/require .-BrowserWindow))

(defn init-window [window url & args]
  (when (nil? @window)
    (reset! window (->> args
                        (apply hash-map)
                        (transform-keys ->camelCaseString)
                        clj->js
                        browser-window.))
    (.loadURL @window (str config/base-url url))
    (.on @window "closed" #(reset! window nil)))
  (.focus @window))
