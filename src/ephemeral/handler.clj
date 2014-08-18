(ns ephemeral.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [ring.middleware.json :refer [wrap-json-params]]
            [compojure.route :as route]
            [ring.middleware.cors :refer [wrap-cors]]
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
  (-> (handler/api app-routes)
      (wrap-json-params)
      (wrap-cors :access-control-allow-origin #".+"
                 :access-control-allow-methods [:get :put :post :delete])))
