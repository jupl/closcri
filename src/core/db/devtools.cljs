(ns core.db.devtools
  (:require [core.config :as config]
            [re-frame.core :refer [dispatch reg-event-db reg-sub]]
            [re-frame.interceptor :refer [->interceptor get-coeffect get-effect]]))

;; May contain Redux Devtools connection.
(defonce devtools-atom (atom nil))

(def available
  "True if Redux Devtools is available."
  (boolean js/window.devToolsExtension))

(defn- sub
  "Handler for Redux Devtools events. Depending on event update re-frame db."
  [message-raw]
  (let [message (js->clj message-raw :keywordize-keys true)
        {type :type state :state {payload-type :type} :payload} message]
    (when (and (= type "DISPATCH") (= payload-type "JUMP_TO_STATE"))
      (let [db (js->clj (js/JSON.parse state) :keywordize-keys true)]
        (dispatch [:devtools db])))))

(defn make-interceptor
  "Create an interceptor that pushes updates to Redux Devtools."
  []
  (->interceptor
   :id :devtools
   :before #(assoc %1 :devtools-event (get-coeffect %1 :event))
   :after #(let [[type & payload] (:devtools-event %1)
                 db (get-effect %1 :db ::not-found)
                 devtools @devtools-atom]
             (when (and devtools (not= db ::not-found))
               (let [action (->> (if payload {:payload payload} {})
                                 (merge {:type type})
                                 (clj->js))
                     state (clj->js db)]
                 (.send devtools action state)))
             (dissoc %1 :devtools-event))))

(defn init!
  "Set up integration with re-frame and Redux DevTools."
  []
  (when available
    (let [devtools (js/devToolsExtension.connect)]
      (reset! devtools-atom devtools)
      (.subscribe devtools sub)
      (reg-event-db :devtools #(get %2 1)))))
