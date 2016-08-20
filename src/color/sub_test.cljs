(ns color.sub-test
  (:require [cljs.test :refer-macros [is]]
            [color.sub :as sub]
            [devcards.core :refer-macros [deftest]]))

(deftest color-test
  (is (= (sub/color {:color "acolor"}) "acolor")))
