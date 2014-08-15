(ns ephemeral.utils
  (:require [clj-time.format :as format-time]
            [clj-time.core :as t]))

(defn create-uuid
  (str (java.util.UUID/randomUUID)))
