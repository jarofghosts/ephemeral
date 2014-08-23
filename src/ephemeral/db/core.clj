(ns ephemeral.db.core
  (:require [taoensso.carmine :as redis]
            [ephemeral.utils :as utils]))

(defn- redis-set [k v]
  (redis/wcar nil (redis/set k v)))

(defn- redis-get [k]
  (redis/wcar nil (redis/get k)))

(defn- inc-accesses [message]
  (assoc message :accesses (inc (:accesses message 0))))

(defn get-message [id]
  (redis-get id))

(defn add-message! [id message expire-time expire-views]
  (redis-set id {:message message
                 :expire expire-time
                 :accesses 0
                 :views expire-views
                 :created (utils/now)})
  id)

(defn add-access! [id]
  (let [message (redis-get id)]
    (redis-set id (inc-accesses message))))
