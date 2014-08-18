(ns ephemeral.handler
  (:use [ring.middleware.json :only [wrap-json-params]])
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ephemeral.core :as ephemeral]
            [noir.response :as response]))

(defroutes app-routes
  (GET "/" [] (response/status 200 (response/json (ephemeral/info))))

  (GET "/message/:id" [id]
       (let [message (ephemeral/lookup-message id)]
         (cond
          (nil? message) (response/status 404 "Not Found")
          :else (response/status 200 (response/json message)))))

  (PUT "/message" {data :params}
       (let [id (ephemeral/create-message data)]
         (cond
          (nil? id) (response/status 400 "Bad Request")
          :else (response/status 201 (response/json {:success true :id (:id id)})))))

  (route/not-found "Not Found"))

(def app
  (wrap-json-params (handler/api app-routes)))
