(defproject gh-commits-category "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.logging "0.4.1"]
                 [com.stuartsierra/component "0.4.0"]
                 [cheshire "5.8.1"]
                 [clj-http "3.9.1"]
                 [environ "1.1.0"]
                 [metosin/compojure-api "2.0.0-alpha30"]
                 [prismatic/schema "1.1.10"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring-cors "0.1.13"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot gh-commits-category.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :uberjar-name "commits-category.jar"
  :test-selectors {:default (complement :integration)
                   :integration :integration })
