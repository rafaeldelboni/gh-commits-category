(ns gh-commits-category.components.http
  (:require [com.stuartsierra.component :as component]
            [clj-http.client :as http]
            [cheshire.core :as json]
            [gh-commits-category.protocols.http-client :as http-client]))

(defrecord Http []
  component/Lifecycle
  (start [this] this)
  (stop  [this] this)

  http-client/HttpClient
  (post! [_this url headers body]
    (http/post
      url
      {:as :json
       :headers headers
       :body (json/encode body)})))

(defn new-http []
  (map->Http {}))
