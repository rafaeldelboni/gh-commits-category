(ns gh-commits-category.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clj-http.client :as http]
            [cheshire.core :as json]
            [environ.core :refer [env]]))

; config component
(def ^:private gh-url "https://api.github.com/graphql")

(def ^:private gh-token (env :gh-token))

; http-client component
(defn post! [body]
  (http/post
    gh-url
    {:as :json
     :headers {:authorization (str "bearer " gh-token)}
     :body (json/encode body)}))

; http/user-commits
(def ^:private graphql-queries
  (read-string (slurp (io/resource "graphql-queries.edn"))))

(defn build-user-commits-variables [user since after]
  {:queryString (str "user:" user " is:public archived:false created:>" since)
   :afterPage after})

(defn build-user-commits-body [queries user since after]
  {:query (:user-commits queries)
   :variables (build-user-commits-variables user since after)})

(defn get-user-commits [user since after acum]
  (let [result
        (get-in
          (post!
            (build-user-commits-body graphql-queries user since after))
          [:body :data :search])
        {edges :edges  {hasNextPage :hasNextPage endCursor :endCursor} :pageInfo} result]
    (let [commits (into acum edges)]
      (if hasNextPage
        (recur user since endCursor commits)
        commits))))

; logic
(defn flatten-commits [user-commits]
  (map #(get-in % [:node :message])
       (reduce into []
               (map #(get-in % [:node :defaultBranchRef :target :history :edges])
                    user-commits))))

;(flatten-commits (get-user-commits "rafaeldelboni" "2016-01-01" nil []))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
