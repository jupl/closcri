(ns app.electron
  (:require [app.config :as config]
            [camel-snake-kebab.core :refer [->camelCaseString]]
            [camel-snake-kebab.extras :refer [transform-keys]]))

(def electron (js/require "electron"))

(def base-url (if config/production
                (str "file://" js/__dirname "/")
                (let [os (js/require "os")]
                  (str "http://" (.hostname os) ":3000/"))))
(def window (atom nil))

(defn new-window [& args]
  (let [browser-window (.-BrowserWindow electron)
        args-map (apply hash-map args)
        camel-args-map (transform-keys ->camelCaseString args-map)
        options (clj->js camel-args-map)]
    (browser-window. options)))

(defn init-window [window & {:keys [url] :or {url "index.html"}}]
  (when (nil? @window)
    (reset! window (new-window))
    (.loadURL @window (str base-url url))
    (.on @window "closed" #(reset! window nil))))

(defn init []
  (let [app (.-app electron)]
    (.on app "ready" #(init-window window))
    (.on app "activate" #(init-window window))
    (.on app "window-all-closed" #(when-not config/osx (.quit app)))))

(set! *main-cli-fn* identity)
