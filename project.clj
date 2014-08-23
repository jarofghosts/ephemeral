(defproject ephemeral "0.1.1"
  :description "self-destructive messages"
  :url "https://github.com/jarofghosts/ephemeral"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [lib-noir "0.8.4"]
                 [clj-time "0.8.0"]
                 [org.clojure/data.json "0.2.5"]
                 [com.taoensso/carmine "2.6.2"]
                 [ring-server "0.3.1"]
                 [ring/ring-json "0.3.1"]
                 [ring-mock "0.1.5"]
                 [ring-cors "0.1.4"]
                 [environ "0.5.0"]
                 [compojure "1.1.8"]]
  :plugins [[lein-ring "0.8.11"] [lein-environ "1.0.0"]]
  :ring {:handler ephemeral.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
