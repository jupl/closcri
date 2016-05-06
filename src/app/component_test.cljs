(ns app.component-test
  (:require [app.component :as app]
            [cljs.test :refer-macros [is]]
            [devcards.core :refer-macros [defcard-rg deftest]]
            [reagent.core]))

(defcard-rg page
  "This is the `app/page` component."
  [app/page])

(deftest page-test)
