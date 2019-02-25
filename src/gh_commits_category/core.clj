(ns gh-commits-category.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clj-http.client :as http]
            [cheshire.core :as json]
            [environ.core :refer [env]]))

(def ^:private gh-url "https://api.github.com/graphql")

(def ^:private gh-token (env :gh-token))

(def ^:private queries
  (read-string (slurp (io/resource "graphql-queries.edn"))))

(defn build-last [last]
  (if (empty? last) "" (str ", after: \"" last "\"")))

(defn build-query-user-repos [query user last]
  {:query (clojure.string/replace
            query
            #"<user>|<last>"
            {"<user>" user "<last>" (build-last last)})})

(defn gh-http-post [user last]
  (http/post
    gh-url
    {:as :json
     :headers {:authorization (str "bearer " gh-token)}
     :body (json/encode (build-query-user-repos (:user-repos queries) user last))}))

(defn get-my-repos [page]
  (get-in (gh-http-post "rafaeldelboni" page) [:body :data :user :repositories]))

(defn paginate-user-repos
  [{nodes :nodes {hasNextPage :hasNextPage endCursor :endCursor} :pageInfo} acum]
  (let [repos (conj acum nodes)]
    (if hasNextPage
      (recur (get-my-repos endCursor) repos) repos)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!" (paginate-user-repos (get-my-repos "") nil)))
