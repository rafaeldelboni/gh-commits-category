(ns gh-commits-category.core
  (:gen-class)
  (:require [com.stuartsierra.component :as component] 
            [gh-commits-category.components.config :as config]
            [gh-commits-category.components.http :as http-cli]
            [gh-commits-category.components.github :as gh]
            [gh-commits-category.logic :as logic]
            [gh-commits-category.github.search :as search]
            [gh-commits-category.server :as server]))

(defn- build-system-map []
  (component/system-map
    :config (config/new-config config/config-map)
    :http (http-cli/new-http)
    :github (component/using (gh/new-gh) [:config :http])))

(def system (atom nil))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (-> (build-system-map)
      (server/start-system! system)))

; (logic/commits->category-map (search/get-user-commits (:github @system) "rafaeldelboni" "2016-01-01" nil []))
