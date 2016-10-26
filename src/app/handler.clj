(ns app.handler
  "Application ring handler."
  (:require
   [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
   [ring.middleware.head :refer [head-response]]
   [ring.middleware.resource :refer [wrap-resource]]
   [ring.util.response :refer [content-type resource-response]]))

(def default-response
  "Default response to show, which is a page."
  (-> (resource-response "index.html" {:root "public"})
      (content-type "text/html")))

(def handler
  "Finalized application Ring handler."
  (-> (partial head-response default-response)
      (wrap-resource "public")
      (wrap-defaults api-defaults)))
