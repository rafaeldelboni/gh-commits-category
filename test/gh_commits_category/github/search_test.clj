(ns gh-commits-category.github.search-test
  (:require [clojure.test :refer :all :as t]
            [gh-commits-category.protocols.github-api :as gh-api]
            [gh-commits-category.github.search :as search]))

(defrecord Github []
  gh-api/GithubApi
  (search! [_this _query {after :afterPage}]
    (if (nil? after)
      {:body {:data {:search {:edges [1 2 3]
                              :pageInfo {:hasNextPage true :endCursor 1}}}}}
      {:body {:data {:search {:edges [4 5 6]
                              :pageInfo {:hasNextPage false :endCursor nil}}}}})))

(def gh (map->Github {}))

(t/deftest get-user-commits-test
  (t/testing "should get paginated results"
    (t/is (= (search/get-user-commits gh "rafa" 2016 nil []) [1 2 3 4 5 6]))))
