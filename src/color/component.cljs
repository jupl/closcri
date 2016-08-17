(ns color.component
  (:require [color.db]
            [color.template :as template]
            [re-frame.core :refer [dispatch subscribe]]))

(def page-actions {:previous-color #(dispatch [:color-previous])
                   :next-color #(dispatch [:color-next])})

(defn page [{:keys [style]}]
  (let [color (subscribe [:color])]
    (fn []
      [template/page {:style style :color @color :actions page-actions}])))
