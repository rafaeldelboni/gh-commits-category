(ns gh-commits-category.protocols.http-client)

(defprotocol HttpClient
  "Protocol for the Http Client"
  (post! [http url headers body]))
