(ns ephemeral.core
  (:require [ephemeral.db.core :as db]
            [ephemeral.utils :as utils]
            [clj-time.format :as format-time]
            [clj-time.core :as t]))

(defn create-message [message {:keys [time views]}]
  (db/add-message (utils/create-uuid) message time views))

(defn log-access [id]
  (db/add-access id))
