(ns lobos.config
  (:require [lobos.connectivity :refer [open-global]]
            [ephemeral.db.schema :refer [db-spec]]))

(open-global db-spec)
