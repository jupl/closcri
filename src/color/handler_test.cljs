(ns color.handler-test
  (:require [cljs.test :refer-macros [is testing]]
            [color.handler :as handler]
            [devcards.core :refer-macros [deftest]]))

(deftest previous-color-test
  (testing "known color"
    (is (= (handler/previous-color "#39cccc" nil) "#ff851b"))
    (is (= (handler/previous-color "#2ecc40" nil) "#39cccc")))
  (testing "unknown color"
    (is (= (handler/previous-color nil nil) "#39cccc"))))

(deftest next-color-test
  (testing "known color"
    (is (= (handler/next-color "#39cccc" nil) "#2ecc40"))
    (is (= (handler/next-color "#ff851b" nil) "#39cccc")))
  (testing "unknown color"
    (is (= (handler/next-color nil nil) "#39cccc"))))
