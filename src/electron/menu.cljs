(ns electron.menu
  "ClojureScript API for Electron menu."
  (:require
   [common.config :as config]
   [electron.window :refer [init-window]]))

(def menu-template
  "Template to render menu in Electron."
  (atom []))

(defn osx-menu-item
  "Create additional menu item for OS X."
  []
  (let [name (-> "electron" js/require .-app .getName)]
    {:label name
     :submenu [{:role "about"}
               {:type "separator"}
               {:role "services" :submenu []}
               {:type "separator"}
               {:role "hide"}
               {:role "hideothers"}
               {:role "unhide"}
               {:type "separator"}
               {:role "quit"}]}))

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

(defn update-menu!
  "Handler for when menu template is updated, which rebuilds and updates menu."
  [_ _ _ template]
  (let [menu (-> "electron" js/require .-Menu)
        osx (= js/process.platform "darwin")
        pre-menu (if osx [(osx-menu-item)] [])
        post-menu (if (identical? config/production true) [] [dev-menu-item])]
    (as-> template x
      (into pre-menu x)
      (into x post-menu)
      (clj->js x)
      (.buildFromTemplate menu x)
      (.setApplicationMenu menu x))))

(defn init-menu
  "Intialize Electron menu and monitor for changes. Call this only once."
  [name]
  (add-watch menu-template :watcher update-menu!)
  (reset! menu-template @menu-template))