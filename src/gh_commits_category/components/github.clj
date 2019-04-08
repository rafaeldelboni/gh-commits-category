(ns gh-commits-category.components.github
  (:require [com.stuartsierra.component :as component]
            [gh-commits-category.protocols.http-client :as http-client]
            [gh-commits-category.protocols.github-api :as gh-api]))

(defrecord Github [config http]
  component/Lifecycle
  (start [this] this)
  (stop  [this] this)

  gh-api/GithubApi
  (search! [_this query-key variables]
    (let [config (:config config)]
      (http-client/post!
        http
        (config :gh-url)
        {:authorization (str "bearer " (config :gh-token))}
        {:query (query-key (config :graphql-queries))
         :variables variables}))))

(defn new-gh []
  (map->Github {}))
