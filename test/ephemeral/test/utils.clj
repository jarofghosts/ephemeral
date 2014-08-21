(ns ephemeral.test.utils
  (:require [clojure.test :refer :all]
            [ephemeral.utils :refer :all]))

(deftest test-app
  (testing "uuid generator"
    (let [uuid-rex #"[\da-f]{8}(-[\da-f]{4}){3}-[\da-f]{12}"]
      (is (not (nil? (re-matches uuid-rex (create-uuid)))))))
  (testing "expired views"
    (is (false? (views-expired? nil [])))
    (is (false? (views-expired? 3 [0 0])))
    (is (true? (views-expired? 3 [0 0 0])))
    (is (true? (views-expired? 3 [0 0 0 0])))))
