(ns common.config
  "Configuration macros used across the project.")

(defmacro when-production
  "Helper for evaluating code if (not) in production for tree shaking."
  [flag & body]
  `(when (identical? core.config/production ~flag)
     ~@body))

(defmacro when-hot-reload
  "Helper for evaluating code if there is (not) hot reload for tree shaking."
  [flag & body]
  `(when (identical? core.config/hot-reload ~flag)
     ~@body))
