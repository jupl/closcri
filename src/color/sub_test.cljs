(ns color.sub-test
  (:require [cljs.test :refer-macros [deftest is]]
            [color.sub :as sut]))

(deftest color-test
  (let [db (atom {:color "acolor"})]
    (is (= @(sut/color db) "acolor"))))
