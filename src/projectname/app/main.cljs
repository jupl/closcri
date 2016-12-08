(ns projectname.app.main
  "Entry point for application."
  (:require
   [projectname.common.config :as config :include-macros true]))

(defn- -main
  "Application entry point."
  []
  (when-not (config/production?)
    (enable-console-print!))

  ;; Take out that nil and start writing!
  nil)
