(ns common.config
  "Configuration macros used across the project.")

#?(:cljs
   (defmacro when-production
     "Helper for evaluating code if (not) in production for tree shaking."
     [flag & body]
     `(when (identical? common.config/production ~flag)
        ~@body)))

#?(:cljs
   (defmacro when-hot-reload
     "Helper for evaluating code if there is (not) hot reload for tree shaking."
     [flag & body]
     `(when (identical? common.config/hot-reload ~flag)
        ~@body)))
