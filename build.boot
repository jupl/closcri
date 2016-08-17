(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[adzerk/boot-cljs              "1.7.228-1" :scope "test"]
                 [adzerk/boot-reload            "0.4.12"    :scope "test"]
                 [binaryage/devtools            "0.8.1"     :scope "test"]
                 [binaryage/dirac               "0.6.3"     :scope "test"]
                 [cljsjs/react-dom              "0.14.3-1"  :scope "test"]
                 [crisptrutski/boot-cljs-test   "0.2.1"     :scope "test"]
                 [devcards                      "0.2.1-7"   :scope "test" :exclusions [cljsjs/react-dom]]
                 [org.clojure/clojure           "1.8.0"     :scope "test"]
                 [powerlaces/boot-cljs-devtools "0.1.1"     :scope "test"]
                 [pandeiro/boot-http            "0.7.3"     :scope "test"]
                 [tolitius/boot-check           "0.1.3"     :scope "test"]
                 [camel-snake-kebab             "0.4.0"]
                 [org.clojure/clojurescript     "1.9.216"]])

(require
 '[adzerk.boot-cljs              :refer [cljs]]
 '[adzerk.boot-reload            :refer [reload]]
 '[crisptrutski.boot-cljs-test   :refer [test-cljs]]
 '[powerlaces.boot-cljs-devtools :refer [cljs-devtools]]
 '[pandeiro.boot-http            :refer [serve]]
 '[tolitius.boot-check           :as    check])

(ns-unmap 'boot.user 'test)

(def closure-opts (atom {:devcards true :output-wrapper :true}))
(def target-path "target")

(task-options! reload {:on-jsload 'core.reload/handle}
               serve {:dir target-path}
               target {:dir #{target-path}}
               test-cljs {:js-env :phantom})

(deftask build []
  (swap! closure-opts assoc-in [:closure-defines 'core.config/production] true)
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
   (sift :include #{#"\.out" #"\.cljs\.edn$" #"^\." #"/\."} :invert true)
   (target)))

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
   (sift :include #{#"\.cljs\.edn$"} :invert true)
   (target)))

(deftask devcards []
  (comp
   (speak)
   (sift :include #{#"^index" #"^electron" #"^package"} :invert true)
   (cljs :optimizations :advanced
         :compiler-options @closure-opts)
   (sift :include #{#"\.out" #"\.cljs\.edn$" #"^\." #"/\."} :invert true)
   (target)))

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
