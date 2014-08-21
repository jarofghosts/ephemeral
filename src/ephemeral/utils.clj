(ns ephemeral.utils
  (:require [clj-time.format :as f]
            [clj-time.core :as t]))

(def formatter (f/formatters :basic-date-time))

(defn create-uuid
  "create a random UUID"
  []
  (str (java.util.UUID/randomUUID)))

(defn now
  "return a date string for now"
  []
  (f/unparse formatter (t/now)))

(defn expired?
  "determine if a message is expired"
  ([date-time] expired? date-time (t/now))
  ([date-time start-time]
    (t/after? start-time (f/parse formatter date-time))))

(defn views-expired? [views accesses]
  (and (not (nil? views)) (>= (count accesses) views)))

(defn time-expired? [expires]
  (and (not (nil? expires)) (expired? expires)))
