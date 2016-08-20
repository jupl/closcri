(ns color.handler)

(def colors
  "List of colors to display."
  ["#39cccc" "#2ecc40" "#ffdc00" "#ff851b"])

(def indexed-colors
  "Build indexed list of colors used to lookup."
  (map vector (iterate inc 0) colors))

(defn- index-of-color
  "Given a color, determine index among list of colors."
  [color]
  (first (for [[index element] indexed-colors :when (= color element)] index)))

(defn next-color
  "Update re-frame data with next color in color list."
  [db _]
  (let [index (or (index-of-color (:color db)) -1)
        color (get colors (mod (+ index 1) (count colors)))]
    (assoc db :color color)))

(defn previous-color
  "Update re-frame data with previous color in color list."
  [db _]
  (let [index (or (index-of-color (:color db)) 1)
        color (get colors (mod (- index 1) (count colors)))]
    (assoc db :color color)))
