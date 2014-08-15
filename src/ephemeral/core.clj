(ns ephemeral.core
  (:require [ephemeral.db.core :as db]
            [ephemeral.utils :as utils]))

(defn info []
  "returns info"
  {:version "0.1.0" :name "ephemeral"})

(defn create-message [{:keys [message time views]}]
  (cond
   (nil? message) nil

   :else (db/add-message (utils/create-uuid) message time views)))

(defn log-access [id]
  "log an access to the message"
  (db/add-access id))

(defn lookup-message [id]
  (let [{:keys [message expire views created accesses]} (db/get-message id)]
    (cond
          (nil? created) nil
          (and (not (nil? views)) (>= (count accesses) views)) nil
          (and (not (nil? expire)) (utils/expired? expire)) nil

          :else (do
                  (if-not (nil? views) (db/add-access id))
                  {:message message :id id}))))
