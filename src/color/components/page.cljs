(ns color.components.page
  (:require [color.db]
            [re-frame.core :refer [dispatch subscribe]]))

;; ---------- Template

(def background-style
  {:transition "background 0.8s ease-out"})

(def button-style
  {:border-radius "50%"
   :background "radial-gradient(white, gainsboro)"
   :border-color "gainsboro"
   :outline "none"})

(def content-style
  {:width "100%"
   :height "100%"
   :display "flex"
   :justify-content "center"
   :align-items "center"
   :background "linear-gradient(rgba(255, 255, 255, 0.4), transparent)"})

(defn- template [{:keys [actions color style]}]
  (let [{:keys [next-color previous-color]} actions]
    [:div {:style (assoc (merge background-style style) :background-color color)}
     [:div {:style content-style}
      [:button {:style button-style :on-click previous-color} "<"]
      "\u00A0"
      [:span "Hello"]
      "\u00A0"
      [:button {:style button-style :on-click next-color} ">"]]]))

;; ---------- Component

(def page-actions {:previous-color #(dispatch [:color-previous])
                   :next-color #(dispatch [:color-next])})

(defn component [{:keys [style]}]
  (let [color (subscribe [:color])]
    (fn []
      [template {:style style :color @color :actions page-actions}])))
