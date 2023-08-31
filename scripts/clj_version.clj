#!/usr/bin/env bb

(require '[babashka.fs :as fs])

(defn parse-version [deps-edn]
  (get-in deps-edn [:deps 'org.clojure/clojure :mvn/version]))

(defn read-deps-edn [path]
  (and (fs/readable? path)
       (edn/read-string (slurp (str path)))))

(def clj-version (comp parse-version read-deps-edn))

(println (or (clj-version "./deps.edn")
             (clj-version (fs/expand-home "~/.clojure/deps.edn"))
             (clj-version "/usr/local/lib/clojure/deps.edn")))
