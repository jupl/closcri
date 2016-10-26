(ns electron.window
  "ClojureScript API for Electron browser windows."
  (:require
   [camel-snake-kebab.core :refer [->camelCaseString]]
   [camel-snake-kebab.extras :refer [transform-keys]]
   [common.config :as config]))

(defn init-window
  "Create a new window (if it doesn't exist already) and focus."
  [window url & args]
  (when (nil? @window)
    (let [browser-window (-> "electron" js/require .-BrowserWindow)]
      (reset! window (->> args
                          (apply hash-map)
                          (transform-keys ->camelCaseString)
                          clj->js
                          browser-window.))
      (.loadURL @window (str config/base-url url))
      (.on @window "closed" #(reset! window nil))))
  (.focus @window))
