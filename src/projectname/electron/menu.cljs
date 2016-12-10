(ns projectname.electron.menu
  "ClojureScript API for Electron menu."
  (:require
   [projectname.common.config :refer-macros [production?]]
   [projectname.electron.window :refer [init-window]]))

(def menu-template
  "Template to render menu in Electron."
  (atom []))

(defn- osx-menu-item
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

(defn- dev-menu-item
  "Create dditional menu item for development."
  []
  (when-not (production?)
    (let [devcards-window (atom nil)
          open-devcards #(init-window devcards-window "devcards.html")]
      {:label "Develop"
       :submenu [{:role "toggledevtools"
                  :accelerator "F12"}
                 {:label "Open Devcards"
                  :accelerator "Shift+F12"
                  :click open-devcards}]})))

(defn- update-menu!
  "Handler for when menu template is updated, which rebuilds and updates menu."
  [_ _ _ template]
  (let [menu (-> "electron" js/require .-Menu)
        osx (= js/process.platform "darwin")
        pre-menu (if osx [(osx-menu-item)] [])
        post-menu (if (production?) [] [(dev-menu-item)])]
    (->> (concat pre-menu template post-menu)
         clj->js
         (.buildFromTemplate menu)
         (.setApplicationMenu menu))))

(defn init-menu
  "Intialize Electron menu and monitor for changes. Call this only once."
  [name]
  (add-watch menu-template :watcher update-menu!)
  (reset! menu-template @menu-template))
