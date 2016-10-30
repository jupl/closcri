(ns app.handler
  "Application ring handler."
  (:require
   [hiccup.page :refer [html5 include-css include-js]]
   [ring.middleware.head :refer [head-response]]
   [ring.middleware.resource :refer [wrap-resource]]))

(def home-page
  "Home page."
  (html5 {:lang "en"}
   [:head
    [:meta {:charset "UTF-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content "initial-scale=1.0"}]
    [:title "App"]
    (include-css "/assets/normalize.css")]
   [:body
    (include-js "/app.js")]))

(def default-response
  "Default response to show."
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body home-page})

(def handler
  "Finalized application Ring handler."
  (wrap-resource (partial head-response default-response) "public"))
