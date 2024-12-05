(ns ringmaster-backend.core
  (:require [compojure.core :refer :all]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as response]
            [cheshire.core :as json]))

;; In-memory state
(def ringmaster (atom nil))         ;; Current Ringmaster
(def players (atom {}))             ;; Registered players: {player-id {:name "Player Name", :wins 0}}

;; Middleware configuration
(def custom-defaults
  (assoc-in site-defaults [:security :anti-forgery] false))

;; Helpers
(defn random-winner [challenger ringmaster]
  (if (< (rand) 0.5) challenger ringmaster))

;; Routes
(defroutes app-routes
  ;; Welcome message
  (GET "/" []
       (-> {:message "Welcome to the Ringmaster Game!"}
           json/generate-string
           response/response
           (response/content-type "application/json")))

  ;; Register a player
(POST "/register" [name]
      (let [id (str (java.util.UUID/randomUUID))]
        (swap! players assoc id {:name name :wins 0})
        (println "Current players: " @players) ;; Add this line to debug
        (-> {:player-id id :message (str "Player " name " registered successfully!")}
            json/generate-string
            response/response
            (response/content-type "application/json"))))


  ;; Claim the ring
(POST "/claim" [player-id]
  (if-let [player (get @players player-id)] ;; Check if player exists
    (do
      (reset! ringmaster player-id) ;; Set the new ringmaster
      (-> {:success true :message (str (:name player) " is now the Ringmaster!")}
          json/generate-string
          response/response
          (response/content-type "application/json")))
    (-> {:success false :message "Player not found!"}
        json/generate-string
        response/response
        (response/content-type "application/json"))))



  ;; Challenge the Ringmaster
  (POST "/challenge" [challenger-id]
        (if-let [ringmaster-id @ringmaster]
          (if-let [challenger (get @players challenger-id)]
            (if (= challenger-id ringmaster-id)
              (-> {:success false :message "You are already the Ringmaster!"}
                  json/generate-string
                  response/response
                  (response/content-type "application/json"))
              (let [winner-id (random-winner challenger-id ringmaster-id)
                    winner (get @players winner-id)]
                (swap! players update-in [winner-id :wins] inc)
                (reset! ringmaster winner-id)
                (-> {:success true
                     :winner (str (:name winner) " is the new Ringmaster!")
                     :ringmaster winner-id}
                    json/generate-string
                    response/response
                    (response/content-type "application/json"))))
            (-> {:success false :message "Challenger not found!"}
                json/generate-string
                response/response
                (response/content-type "application/json")))
          (-> {:success false :message "The ring is unclaimed! Someone must claim it first."}
              json/generate-string
              response/response
              (response/content-type "application/json"))))

  ;; Get the leaderboard
  (GET "/leaderboard" []
       (let [sorted-leaderboard (sort-by #(-> % val :wins) > @players)]
         (-> {:leaderboard (map (fn [[id {:keys [name wins]}]]
                                  {:name name :wins wins}) sorted-leaderboard)}
             json/generate-string
             response/response
             (response/content-type "application/json"))))

  ;; Get current Ringmaster
  (GET "/status" []
       (if-let [ringmaster-id @ringmaster]
         (let [ringmaster (get @players ringmaster-id)]
           (-> {:ringmaster (:name ringmaster)}
               json/generate-string
               response/response
               (response/content-type "application/json")))
         (-> {:message "The ring is unclaimed!"}
             json/generate-string
             response/response
             (response/content-type "application/json")))))

;; Use middleware
(def app
  (wrap-defaults app-routes custom-defaults))
