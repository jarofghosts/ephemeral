(ns lobos.migrations
  (:refer-clojure :exclude [alter drop bigint boolean
                            char double float time])
  (:use (lobos [migration :only [defmigration]]
               core schema config helpers)))

(defmigration add-messages-table
  (up [] (create (table :messages
                        (varchar :id 36 :primary-key)
                        (text :message)
                        (varchar :expire 25)
                        (integer :views)
                        (varchar :created 25))))
  (down [] (drop (table :messages))))

(defmigration add-accesses-table
  (up [] (create (table :accesses
                        (varchar :message_id 36)
                        (varchar :created 25))))
  (down [] (drop (table :accesses))))
