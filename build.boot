(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[adzerk/boot-cljs            "1.7.228-1" :scope "test"]
                 [adzerk/boot-reload          "0.4.7"     :scope "test"]
                 [binaryage/devtools          "0.6.1"     :scope "test"]
                 [binaryage/dirac             "0.3.0"     :scope "test"]
                 [crisptrutski/boot-cljs-test "0.2.1"     :scope "test"]
                 [devcards                    "0.2.1-6"   :scope "test"]
                 [jupl/boot-cljs-devtools     "0.1.0"     :scope "test"]
                 [org.clojure/clojure         "1.8.0"     :scope "test"]
                 [pandeiro/boot-http          "0.7.3"     :scope "test"]
                 [org.clojure/clojurescript   "1.8.40"]
                 [re-frame                    "0.7.0"]
                 [reagent                     "0.6.0-alpha2"]])

(require
 '[adzerk.boot-cljs            :refer [cljs]]
 '[adzerk.boot-reload          :refer [reload]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]]
 '[jupl.boot-cljs-devtools     :refer [cljs-devtools]]
 '[pandeiro.boot-http          :refer [serve]])

(ns-unmap 'boot.user 'test)

(task-options! reload {:on-jsload 'reagent.core/force-update-all}
               serve {:dir "target"}
               test-cljs {:js-env :phantom})

(def closure-opts (atom {:devcards true :output-wrapper :true}))

(deftask build []
  (swap! closure-opts assoc-in [:closure-defines 'app.config/production] true)
  (comp
   (speak)
   (sift :include #{#"^devcards"} :invert true)
   (cljs :optimizations :advanced
         :compiler-options @closure-opts)
   (sift :include #{#"\.out" #"\.cljs\.edn$" #"^\." #"/\."} :invert true)))

(deftask dev []
  (comp
   (serve)
   (watch)
   (speak)
   (reload)
   (cljs-devtools)
   (cljs :source-map true
         :optimizations :none
         :compiler-options @closure-opts)
   (sift :include #{#"\.cljs\.edn$"} :invert true)))

(deftask devcards []
  (comp
   (speak)
   (sift :include #{#"^devcards" #"/" #"\.clj(s|c)$"})
   (cljs :optimizations :advanced
         :compiler-options @closure-opts)
   (sift :include #{#"\.out" #"\.cljs\.edn$"} :invert true)))

(deftask test []
  (comp
   (speak)
   (test-cljs)))

(deftask tdd []
  (comp
   (watch)
   (speak)
   (test-cljs)))
