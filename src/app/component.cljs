(ns app.component
  (:require [color.component :as color]))

(def page-style {:width "100%" :height "100%"})

(defn page []
  [color/page {:style page-style}])
