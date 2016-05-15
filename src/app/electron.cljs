(ns app.electron
  (:require [app.config :as config]
            [app.electron.window :refer [init-window]]))

(def app (-> "electron" js/require .-app))

(defn init []
  (let [main-window (atom nil)
        open-main-window #(init-window main-window "index.html")]
    (.on app "ready" open-main-window)
    (.on app "activate" open-main-window)
    (.on app "window-all-closed" #(when-not config/osx (.quit app)))))

(set! *main-cli-fn* identity)
