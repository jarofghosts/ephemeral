(ns ephemeral.core
  (:require [ephemeral.db.core :as db]
            [ephemeral.utils :as utils]))

(defn info []
  "returns info"
  {:version "0.1.0" :name "ephemeral"})

(defn create-message [{:keys [message time views]}]
  (cond
   (nil? message) nil

   :else (db/add-message! (utils/create-uuid) message time views)))

(defn log-access [id]
  "log an access to the message"
  (db/add-access! id))

(defn lookup-message [id]
  (let [{:keys [message expire views created accesses]} (db/get-message id)]
    (cond
          (nil? created) nil
          (utils/views-expired? views accesses) nil
          (utils/time-expired? expire) nil

          :else (do
                 (if-not (nil? views) (log-access id))
                 {:message message :id id}))))
