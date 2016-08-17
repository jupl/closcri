(ns color.handler-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [color.handler :as sut]))

(deftest previous-color-test
  (testing "known color"
    (is (= (sut/previous-color {:color "#39cccc"} nil) {:color "#ff851b"}))
    (is (= (sut/previous-color {:color "#2ecc40"} nil) {:color "#39cccc"})))
  (testing "unknown color"
    (is (= (sut/previous-color nil nil) {:color "#39cccc"}))))

(deftest next-color-test
  (testing "known color"
    (is (= (sut/next-color {:color "#39cccc"} nil) {:color "#2ecc40"}))
    (is (= (sut/next-color {:color "#ff851b"} nil) {:color "#39cccc"})))
  (testing "unknown color"
    (is (= (sut/next-color nil nil) {:color "#39cccc"}))))
