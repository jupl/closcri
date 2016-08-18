(ns color.handler)

(def colors ["#39cccc" "#2ecc40" "#ffdc00" "#ff851b"])
(def indexed-colors (map vector (iterate inc 0) colors))

(defn- index-of-color [color]
  (first (for [[index element] indexed-colors :when (= color element)] index)))

(defn next-color [db _]
  (let [index (or (index-of-color (:color db)) -1)
        color (get colors (mod (+ index 1) (count colors)))]
    (assoc db :color color)))

(defn previous-color [db _]
  (let [index (or (index-of-color (:color db)) 1)
        color (get colors (mod (- index 1) (count colors)))]
    (assoc db :color color)))
