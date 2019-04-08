(ns gh-commits-category.controllers
  (:require [clojure.tools.logging :as log]
            [gh-commits-category.logic :as logic]
            [gh-commits-category.github.search :as search]))

(defn get-categories
  [github user year-since]
  (try
    {:status 200
     :result (logic/commits->category-map 
               (search/get-user-commits github user year-since nil []))}
    (catch Exception e
      (log/error e)
      {:status 500
       :error (.getMessage e)})))
