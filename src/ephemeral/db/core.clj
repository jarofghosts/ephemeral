(ns ephemeral.db.core
  (:use korma.core)
  (:use korma.db)
  (:require [ephemeral.db.schema :as schema])
  (:require [environ.core :refer [env]])
  (:require [ephemeral.utils :as utils]))

(defdb db schema/db-spec)

(declare messages)

(defentity accesses
  (belongs-to messages))

(defentity messages
  (has-many accesses {:fk :message_id}))

(defn get-message [id]
  (first (select messages
                 (with accesses)
                 (where {:id id})
                 (limit 1))))

(defn add-message! [id message expire-time expire-views]
  (insert messages (values {:id id
                            :message message
                            :expire expire-time
                            :views expire-views
                            :created (utils/now)})))

(defn add-access! [id]
  (insert accesses (values {:message_id id
                            :created (utils/now)})))
