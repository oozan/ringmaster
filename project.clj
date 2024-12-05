(defproject ringmaster-backend "0.1.0-SNAPSHOT"
  :description "A backend for the Ringmaster app"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
:dependencies [[org.clojure/clojure "1.11.1"]
               [ring/ring-defaults "0.3.3"]
               [compojure "1.6.2"]
               [ring/ring-json "0.5.1"]
               [cheshire "5.11.0"]]
  :plugins [[lein-ring "0.12.5"]] ;; Add the lein-ring plugin
  :main ^:skip-aot ringmaster-backend.core
  :ring {:handler ringmaster-backend.core/app} ;; Add the handler for the Ring server
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
