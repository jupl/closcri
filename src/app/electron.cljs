(ns app.electron
  (:require [app.config :as config]
            [camel-snake-kebab.core :refer [->camelCaseString]]
            [camel-snake-kebab.extras :refer [transform-keys]]))

(def electron (js/require "electron"))
(def app (.-app electron))
(def browser-window (.-BrowserWindow electron))

(def window (atom nil))

(defn new-window [& args]
  (let [args-map (apply hash-map args)
        camel-args-map (transform-keys ->camelCaseString args-map)
        options (clj->js camel-args-map)]
    (browser-window. options)))

(defn init-window [window & {:keys [url] :or {url "index.html"}}]
  (when (nil? @window)
    (reset! window (new-window))
    (.loadURL @window (str config/base-url url))
    (.on @window "closed" #(reset! window nil)))
  (.focus @window))

(defn init []
  (.on app "ready" #(init-window main-window))
  (.on app "activate" #(init-window main-window))
  (.on app "window-all-closed" #(when-not config/osx (.quit app))))

(set! *main-cli-fn* identity)
