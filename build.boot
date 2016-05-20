(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[adzerk/boot-cljs            "1.7.228-1"      :scope "test"]
                 [adzerk/boot-reload          "0.4.7"          :scope "test"]
                 [boot/core                   "2.4.2"          :scope "test"]
                 [binaryage/devtools          "0.6.1"          :scope "test"]
                 [binaryage/dirac             "0.4.0"          :scope "test"]
                 [crisptrutski/boot-cljs-test "0.2.1"          :scope "test"]
                 [devcards                    "0.2.1-6"        :scope "test"]
                 [jupl/boot-cljs-devtools     "0.1.0"          :scope "test"]
                 [org.clojure/clojure         "1.8.0"          :scope "test"]
                 [pandeiro/boot-http          "0.7.3"          :scope "test"]
                 [tolitius/boot-check         "0.1.2-SNAPSHOT" :scope "test"]
                 [camel-snake-kebab           "0.4.0"]
                 [org.clojure/clojurescript   "1.8.40"]])

(require
 '[adzerk.boot-cljs            :refer [cljs]]
 '[adzerk.boot-reload          :refer [reload]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]]
 '[jupl.boot-cljs-devtools     :refer [cljs-devtools]]
 '[pandeiro.boot-http          :refer [serve]]
 '[tolitius.boot-check         :as    check])

(ns-unmap 'boot.user 'test)

(task-options! reload {:on-jsload 'app.main/reload}
               serve {:dir "target"}
               test-cljs {:js-env :phantom})

(def closure-opts (atom {:devcards true :output-wrapper :true}))

(deftask build []
  (swap! closure-opts assoc-in [:closure-defines 'app.config/production] true)
  (comp
   (speak)
   (sift :include #{#"^devcards"} :invert true)
   (cljs :ids #{"electron"}
         :optimizations :simple
         :optimize-constants true
         :static-fns true
         :compiler-options @closure-opts)
   (cljs :ids #{"index"}
         :optimizations :advanced
         :compiler-options @closure-opts)
   (sift :include #{#"\.out" #"\.cljs\.edn$" #"^\." #"/\."} :invert true)))

(deftask dev
  [d no-devcards bool "Flag to indicate if devcards should be excluded. Defaults to false."
   p port PORT   int  "The port number to start the server in."]
  (comp
   (serve :port port)
   (watch)
   (speak)
   (if no-devcards
     (sift :include #{#"^devcards"} :invert true)
     identity)
   (reload)
   (cljs-devtools)
   (cljs :source-map true
         :optimizations :none
         :compiler-options @closure-opts)
   (sift :include #{#"\.cljs\.edn$"} :invert true)))

(deftask devcards []
  (comp
   (speak)
   (sift :include #{#"^index" #"^electron" #"^package"} :invert true)
   (cljs :optimizations :advanced
         :compiler-options @closure-opts)
   (sift :include #{#"\.out" #"\.cljs\.edn$" #"^\." #"/\."} :invert true)))

(deftask analyze []
  (comp
   (sift :include #{#"\.clj(s|c)$"})
   (check/with-yagni)
   (check/with-eastwood)
   (check/with-kibit)
   (check/with-bikeshed)))

(deftask test []
  (comp
   (speak)
   (test-cljs)))
