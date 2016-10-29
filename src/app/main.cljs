(ns app.main
  "Entry point for main client-side application."
  (:require
   [common.config :refer-macros [when-production]]))

(defn- -main
  "Application entry point."
  []
  (when-production false
    (enable-console-print!))

  ;; Take out that nil and start writing!
  nil)
