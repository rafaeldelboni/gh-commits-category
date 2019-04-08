(ns gh-commits-category.components.webserver
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty :refer :all :as jetty]
            [ring.middleware.cors :refer [wrap-cors]]))

(defrecord WebServer [config app github]
  component/Lifecycle
  (start [this]
    (let [config (:config config)]
      (log/info "Starting Web App " (config :port))
      (assoc this :http-server
             (jetty/run-jetty
               (wrap-cors
                 (app github)
                 :access-control-allow-origin (re-pattern (config :cors))
                 :access-control-allow-methods [:get :put :post :delete])
               {:port (config :port)
                :join? false}))))
  (stop [this]
    (log/info "Stopping Web App")
    (.stop (:http-server this))
    (dissoc this :http-server)
    this))

(defn new-webserver [app]
(map->WebServer {:app app}))
