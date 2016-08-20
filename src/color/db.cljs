(ns color.db
  (:require [core.db :refer [add-init-dispatch! reg-event-db]]
            [color.handler :as handler]
            [color.sub :as sub]
            [re-frame.core :refer [reg-sub]]
            [re-frame.std-interceptors :refer [path]]))

;; Add color subscriptions
(reg-sub :color sub/color)

;; Add color action handlers
(reg-event-db :color-previous [(path :color)] handler/previous-color)
(reg-event-db :color-next [(path :color)] handler/next-color)

;; Run actions when application starts up
(add-init-dispatch! [:color-next])
