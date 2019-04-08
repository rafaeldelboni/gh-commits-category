(ns gh-commits-category.services
  (:require [compojure.api.sweet :refer [api undocumented context GET POST PUT DELETE]]
            [compojure.route :as route]
            [gh-commits-category.schemas :as schm]
            [gh-commits-category.controllers :as ctrl]
            [ring.util.http-response :as http]
            [schema.core :as s]))

(defn routes [github]
  (context "/api" []
           :tags ["api"]

           (GET "/categorize-commits/:id/:year-since" []
                :return schm/Categories
                :path-params [id :- s/Str year-since :- s/Int]
                :summary "Get commit categories by username and year since"
                (let [{error :error result :result status :status}
                      (ctrl/get-categories github id year-since)]
                  (case status
                    200 (http/ok result)
                    (http/internal-server-error {:reason error}))))
           ))

(defn app [github]
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "clojure-social-media-connections"
                    :description "API that extract user infos from Github's
                                 GraphQL API."}
             :tags [{:name "github commit semantic",
                     :description "API that extract user infos from Github"}]}}}

    (routes github)

    (undocumented
      (route/not-found
        (http/ok {:reason "404 Not Found"})))))
