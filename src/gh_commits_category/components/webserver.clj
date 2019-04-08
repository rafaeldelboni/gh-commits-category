(ns gh-commits-category.components.webserver
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty :refer :all :as jetty]))

(defrecord WebServer [config app github]
  component/Lifecycle
  (start [this]
    (log/info "Starting Web App")
    (assoc this :http-server
           (jetty/run-jetty
             (app github)
             {:port (get-in config [:config :port])
              :join? false})))
  (stop [this]
    (log/info "Stopping Web App")
    (.stop (:http-server this))
    (dissoc this :http-server)
    this))

(defn new-webserver [app]
(map->WebServer {:app app}))
