(ns gh-commits-category.protocols.github-api)

(defprotocol GithubApi
  "Protocol for the Http Client"
  (search! [github query-key variables]))
