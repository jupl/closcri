(ns app.electron
  (:require [app.config :as config]
            [camel-snake-kebab.core :refer [->camelCaseString]]
            [camel-snake-kebab.extras :refer [transform-keys]]))

(def electron (js/require "electron"))
(def os (js/require "os"))

(def base-url (if config/production
                (str "file://" js/__dirname "/")
                (str "http://" (.hostname os) ":3000/")))
(def window (atom nil))

(defn new-window [& args]
  (let [browser-window (.-BrowserWindow electron)
        args-map (apply hash-map args)
        camel-args-map (transform-keys ->camelCaseString args-map)
        options (clj->js camel-args-map)]
    (browser-window. options)))

(defn init-window []
  (reset! window (new-window))
  (.loadURL @window (str base-url "index.html"))
  (.on @window "closed" #(reset! window nil)))

(defn init []
  (let [app (.-app electron)]
    (.on app "ready" init-window)
    (.on app "activate" #(when (nil? @window) (init-window)))
    (.on app "window-all-closed" #(when-not config/osx (.quit app)))))

(set! *main-cli-fn* identity)
