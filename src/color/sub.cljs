(ns color.sub
  (:require [reagent.ratom :refer-macros [reaction]]))

(defn color [db]
  (reaction (:color @db)))
