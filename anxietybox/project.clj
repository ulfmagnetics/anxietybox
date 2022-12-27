(defproject anxietybox "0.1.0-SNAPSHOT"
  :description "Code for Anxietybox.com"
  :url "http://anxietybox.com/source"
  :dependencies [[org.clojure/clojure "1.6.0"]
                  [compojure "1.3.1"]
                  [org.clojure/java.jdbc "0.3.6"]
                  [enlive "1.1.5"]
                  [postgresql/postgresql "8.4-702.jdbc4"]
                  [hiccup "1.0.5"]
                  [garden "1.2.5"]
                  [clj-http "1.0.1"]
                  [cheshire "5.4.0"]
                  [com.taoensso/timbre "4.10.0"
                    :exclusions [org.clojure/tools.reader]]
                  [environ "1.1.0"]
                  [com.draines/postal "2.0.5"]
                  ]
  :plugins [[lein-ring "0.12.5"]
            [lein-environ "1.1.0"]
            ]
  :ring {:handler anxietybox.handler/app}
  :resource-paths ["src/resources"]

  :profiles {:dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]
             ;; only edit :profiles/* in profiles.clj
             :profiles/dev  {}
             :profiles/test {}
             :project/dev {:dependencies [
                             [ring-mock "0.1.5"]
                             [javax.servlet/servlet-api "2.5"]
                           ]}
             :project/test {}}
  )

