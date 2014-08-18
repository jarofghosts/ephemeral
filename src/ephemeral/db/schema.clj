(ns ephemeral.db.schema
  (:require [environ.core :refer [env]]))

(def full-db-uri
  (str (env :eph-db-location) "/" (env :eph-db-name)))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :user (env :eph-db-user)
              :password (env :eph-db-pass)
              :subname full-db-uri})
