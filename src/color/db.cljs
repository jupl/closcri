(ns color.db
  (:require [core.db :refer [add-init-dispatch! register-handler]]
            [color.handler :as handler]
            [color.sub :as sub]
            [re-frame.core :refer [register-sub]]))

;; Add color subscriptions
(register-sub :color sub/color)

;; Add color action handlers
(register-handler :color-previous handler/previous-color)
(register-handler :color-next handler/next-color)

;; Run actions when application starts up
(add-init-dispatch! [:color-next])
