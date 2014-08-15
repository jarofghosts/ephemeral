(ns ephemeral.db.core
  (:use korma.core
        [korma.db :only (defdb)]))

(defdb db (postgres {:db ephemeral
                     :user ephemeral
                     :password derpitydoo}))

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
