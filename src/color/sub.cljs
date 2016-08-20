(ns color.sub
  (:require [reagent.ratom :refer-macros [reaction]]))

(defn color
  "Build a reaction from app-db to extract color."
  [db]
  (reaction (:color @db)))
