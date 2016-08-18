(ns app.components.page
  (:require [color.components.page :as color.page]))

;; ---------- Template

(def page-style {:width "100%" :height "100%"})

(defn- template []
  [color.page/component {:style page-style}])

;; ---------- Component

(def component template)
