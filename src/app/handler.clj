(ns app.handler
  "Application ring handler."
  (:require
   [ring.middleware.head :refer [head-response]]
   [ring.middleware.resource :refer [wrap-resource]]
   [ring.util.response :refer [content-type resource-response]]))

(def default-response
  "Default response to show, which is a page."
  (content-type (resource-response "public/index.html") "text/html"))

(def handler
  "Finalized application Ring handler."
  (wrap-resource (partial head-response default-response) "public"))
