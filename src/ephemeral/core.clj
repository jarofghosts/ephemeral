(ns ephemeral.core
  (:require [ephemeral.db.core :as db]
            [ephemeral.utils :as utils]))

(defn info
  "returns info"
  []
  {:version "0.1.1" :name "ephemeral"})

(defn create-message
  "adds message to db as long as there is at least a message"
  [{:keys [message time views]}]
  (if (nil? message) nil
    (db/add-message! (utils/create-uuid) message time views)))

(defn log-access
  "log an access to the message"
  [id]
  (db/add-access! id))

(defn lookup-message
  "attempt to load message, gated by expirations"
  [id]
  (let [{:keys [message expire views created accesses]} (db/get-message id)]
    (cond
          (nil? created) nil
          (utils/views-expired? views accesses) nil
          (utils/time-expired? expire) nil

          :else (do
                  (if-not (nil? views) (log-access id))
                  {:message message :id id}))))
