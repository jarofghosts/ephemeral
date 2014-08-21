(ns ephemeral.test.handler
  (:require [clojure.test :refer :all]
            [ephemeral.handler :refer :all]
            [clojure.data.json :as json]
            [ring.mock.request :as mock]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:headers response) {"Content-Type" "application/json; charset=utf-8"}))
      (is (= (keys (json/read-str (:body response))) ["name" "version"]))))
  
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
