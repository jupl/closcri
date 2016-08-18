(ns app.components.page-test
  (:require [app.components.page :as app.page]
            [cljs.test :refer-macros [is]]
            [devcards.core :refer-macros [defcard-rg deftest]]
            [reagent.core]))

(defcard-rg page
  "This is the `app/page` component."
  [app.page/component])

(deftest page-test)
