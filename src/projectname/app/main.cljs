(ns projectname.app.main
  "Entry point for main client-side application."
  (:require
   [projectname.common.config :refer-macros [production?]]))

(defn- -main
  "Application entry point."
  []
  (when-not (production?)
    (enable-console-print!))

  ;; Take out that nil and start writing!
  nil)
