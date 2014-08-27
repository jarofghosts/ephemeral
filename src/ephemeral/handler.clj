(ns ephemeral.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [ring.middleware.json :refer [wrap-json-params]]
            [compojure.route :as route]
            [ring.middleware.cors :refer [wrap-cors]]
            [ephemeral.core :as ephemeral]
            [noir.response :as response]))

(defn respond
  ([body] (respond 200 body))
  ([code body]
  (->> body
       (response/json)
       (response/status code))))

(defroutes app-routes
  (GET "/" [] (respond (ephemeral/info)))

  (GET "/message/:id" [id]
       (if-let [message (ephemeral/lookup-message id)]
          (respond message)
          (response/status 404 "Not Found")))

  (PUT "/message" {data :params}
       (if-let [id (ephemeral/create-message data)]
          (respond 201 {:success true :id id})
          (response/status 400 "Bad Request")))

  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (wrap-json-params)
      (wrap-cors :access-control-allow-origin #".+"
                 :access-control-allow-methods [:get :put :post :delete])))
