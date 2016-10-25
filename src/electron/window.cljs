(ns electron.window
  "ClojureScript API for Electron browser windows."
  (:require
   [camel-snake-kebab.core :refer [->camelCaseString]]
   [camel-snake-kebab.extras :refer [transform-keys]]
   [common.config :as config]))

(def browser-window
  "Electron browser window class."
  (-> "electron" js/require .-BrowserWindow))

(defn init-window
  "Create a new window (if it doesn't exist already) and focus."
  [window url & args]
  (when (nil? @window)
    (reset! window (->> args
                        (apply hash-map)
                        (transform-keys ->camelCaseString)
                        clj->js
                        browser-window.))
    (.loadURL @window (str config/base-url url))
    (.on @window "closed" #(reset! window nil)))
  (.focus @window))
