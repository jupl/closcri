(ns app.config)

(goog-define production false)

(when-not production
  (enable-console-print!))
