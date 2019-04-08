(ns gh-commits-category.github.search
  (:require [gh-commits-category.protocols.github-api :as gh-api]))

(defn build-user-commits-variables [user since after]
  {:queryString
   (str "user:" user " is:public archived:false created:>" since "-01-01")
   :afterPage
   after})

(defn get-user-commits
  [github user since after acum]
  (let [result
        (get-in
          (gh-api/search!
            github
            :user-commits
            (build-user-commits-variables user since after))
          [:body :data :search])
        {edges :edges {hasNextPage :hasNextPage endCursor :endCursor} :pageInfo} result]
    (let [commits (into acum edges)]
      (if hasNextPage
        (recur github user since endCursor commits)
        commits))))
