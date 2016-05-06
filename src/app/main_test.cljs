(ns app.main-test
  (:require [cljs.test :refer-macros [is]]
            [devcards.core :refer-macros [deftest]]))

(deftest simple-test
  (is (= (+ 2 2) 4)))
