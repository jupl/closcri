(ns electron.menu
  "ClojureScript API for Electron menu."
  (:require
   [common.config :as config]
   [electron.window :refer [init-window]]))

(def app
  "Electron application instance."
  (-> "electron" js/require .-app))

(def menu
  "Electron Menu class."
  (-> "electron" js/require .-Menu))

(def menu-template
  "Template to render menu in Electron."
  (atom []))

(def osx-menu-item
  "Additional menu item for OS X."
  {:label (.getName app)
   :submenu [{:role "about"}
             {:type "separator"}
             {:role "services" :submenu []}
             {:type "separator"}
             {:role "hide"}
             {:role "hideothers"}
             {:role "unhide"}
             {:type "separator"}
             {:role "quit"}]})

(def dev-menu-item
  "Additional menu item for a development environment."
  (when (identical? config/production false)
    (let [devcards-window (atom nil)
          open-devcards #(init-window devcards-window "devcards.html")
          toggle-devtools #(.toggleDevTools %2)]
      {:label "Develop"
       :submenu [{:label "Toggle Developer Tools"
                  :accelerator "F12"
                  :click toggle-devtools}
                 {:label "Open Devcards"
                  :accelerator "Shift+F12"
                  :click open-devcards}]})))

(def update-menu!
  "Handler for when menu template is updated, which rebuilds and updates menu."
  (let [pre-menu (if config/osx [osx-menu-item] [])
        post-menu (if (identical? config/production true) [] [dev-menu-item])]
    #(as-> %4 x
       (into pre-menu x)
       (into x post-menu)
       (clj->js x)
       (.buildFromTemplate menu x)
       (.setApplicationMenu menu x))))

;; When application starts, initialize menu and set up for watching
(.on app "ready" (fn []
                   (add-watch menu-template :watcher update-menu!)
                   (reset! menu-template @menu-template)))
