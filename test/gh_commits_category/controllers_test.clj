(ns gh-commits-category.controllers-test
  (:require [clojure.test :refer :all :as t]
            [clojure.string :as str]
            [gh-commits-category.protocols.github-api :as gh-api]
            [gh-commits-category.controllers :as ctrl]))

(defrecord Github []
  gh-api/GithubApi
  (search! [_this _query {query :queryString}]
    (if (str/includes? query "error")
      (throw (Exception. "my exception message"))
      {:body
       {:data
        {:search
         {:edges [] :pageInfo {:hasNextPage false :endCursor nil}}}}})))

(def gh (map->Github {}))

(t/deftest get-categories-test
  (t/testing "should get right http status and result"
    (t/is (= (ctrl/get-categories gh "rafa" 2016)
             {:status 200, :result {}}))
    (t/is (= (ctrl/get-categories gh "error" 2016)
             {:status 500, :error "my exception message"}))))
