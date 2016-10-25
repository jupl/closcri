(ns electron.main
  "Entry point for the main process."
  (:require
   [common.config :as config]
   [electron.menu]
   [electron.window :refer [init-window]]))

(def app
  "Electron application instance."
  (-> "electron" js/require .-app))

(defn main
  "Configure and bootstrap Electron application."
  []
  (let [main-window (atom nil)
        open-main-window #(init-window main-window "app.html")]
    (.on app "ready" open-main-window)
    (.on app "activate" open-main-window)
    (.on app "window-all-closed" #(when-not config/osx (.quit app)))))

;; Required for a Node application
(set! *main-cli-fn* identity)
