(ns electron.menu
  (:require [core.config :as config]
            [electron.window :refer [init-window]]))

(def electron (js/require "electron"))
(def app (.-app electron))
(def menu (.-Menu electron))

(def menu-template (atom []))
(def osx-menu-item {:label (.getName app)
                    :submenu [{:label "Quit"
                               :accelerator "Command+Q"
                               :click #(.quit app)}]})

(if-not config/production
  (let [devcards-window (atom nil)
        open-devcards #(init-window devcards-window "devcards.html")
        toggle-devtools #(.toggleDevTools %2)]
    (def dev-menu-item {:label "Develop"
                        :submenu [{:label "Toggle Developer Tools"
                                   :accelerator "F12"
                                   :click toggle-devtools}
                                  {:label "Open Devcards"
                                   :accelerator "Shift+F12"
                                   :click open-devcards}]})))

(let [pre-menu (if config/osx [osx-menu-item] [])
      post-menu (if config/production [] [dev-menu-item])]
  (defn update-menu! [_ _ _ menu-template]
    (as-> menu-template x
      (into pre-menu x)
      (into x post-menu)
      (clj->js x)
      (.buildFromTemplate menu x)
      (.setApplicationMenu menu x))))

(.on app "ready" (fn []
                   (add-watch menu-template :watcher update-menu!)
                   (reset! menu-template @menu-template)))
