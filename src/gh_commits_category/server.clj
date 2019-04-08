(ns gh-commits-category.server
  (:require [com.stuartsierra.component :as component]))

(defn start-system! [system-map system]
  (->> system-map
       component/start
       (reset! system)))

(defn stop-system! [system]
  (swap!
    system
    (fn [s] (when s (component/stop s)))))
