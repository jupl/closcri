(ns color.db
  (:require [core.db :refer [add-init-dispatch! register-handler]]
            [color.handler :as handler]
            [color.sub :as sub]
            [re-frame.core :refer [register-sub]]]))

(register-sub :color sub/color)

(register-handler :color-previous handler/previous-color)
(register-handler :color-next handler/next-color)

(add-init-dispatch! [:color-next])
