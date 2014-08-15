(ns ephemeral.db.core
  (:use [environ.core :only (env)])
  (:use korma.core)
  (:use korma.db))

(defdb db (postgres {:db (env :eph-db-name)
                     :user (env :eph-db-user)
                     :password (env :eph-db-pass)}))

(defentity accesses)

(defentity messages
  (has-many accesses))

(defn get-message [id]
  (first (select messages
                 (with accesses)
                 (where {:id id})
                 (limit 1))))

(defn add-message [id message expire-time expire-views]
  (insert messages (values {:id id
                            :message message
                            :expire expire-time
                            :views expire-views
                            :created (new java.util.Date)})))

(defn add-access [id]
  (insert accesses (values {:id id
                            :created (new java.util.Date)})))
