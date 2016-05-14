(ns app.electron
  (:require [app.config :as config]
            [camel-snake-kebab.core :refer [->camelCaseString]]
            [camel-snake-kebab.extras :refer [transform-keys]]))

(def electron (js/require "electron"))
(def app (.-app electron))
(def browser-window (.-BrowserWindow electron))

(def main-window (atom nil))

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

(defn init []
  (let [open-main-window #(init-window main-window "index.html")]
    (.on app "ready" open-main-window)
    (.on app "activate" open-main-window)
    (.on app "window-all-closed" #(when-not config/osx (.quit app)))))

(set! *main-cli-fn* identity)
