(ns electron.main
  (:require [core.config :as config]
            [electron.menu]
            [electron.window :refer [init-window]]))

(def app
  "Electron application instance."
  (-> "electron" js/require .-app))

(defn init
  "Configure and bootstrap Electron application."
  []
  (let [main-window (atom nil)
        open-main-window #(init-window main-window "index.html")]
    (.on app "ready" open-main-window)
    (.on app "activate" open-main-window)
    (.on app "window-all-closed" #(when-not config/osx (.quit app)))))

;; Required for a Node application
(set! *main-cli-fn* identity)
