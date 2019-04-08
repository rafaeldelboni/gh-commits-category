(ns gh-commits-category.components.config
  (:require [com.stuartsierra.component :as component]
            [clojure.java.io :as io]
            [environ.core :refer [env]]))

(defrecord Config [config]
  component/Lifecycle
  (start [this] this)
  (stop  [this] this))

(def config-map
  {:gh-url "https://api.github.com/graphql"
   :gh-token (env :gh-token)
   :graphql-queries (read-string (slurp (io/resource "graphql-queries.edn")))})

(defn new-config [input-map] (map->Config {:config (or input-map config-map)}))
