(ns lobos.config
  (:require [lobos.connectivity :refer [open-global]])
  (:require [ephemeral.db.schema :refer [db-spec]])

(open-global db-spec)
