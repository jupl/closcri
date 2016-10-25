(ns common.devcards
  "Entry point for devcards, referencing all devcards."
  (:require
   ;; Require any devcards you want to show
   [devcards.core :refer-macros [start-devcard-ui!]]))

(defn main
  "Configure and bootstrap devcards."
  []
  (start-devcard-ui!))