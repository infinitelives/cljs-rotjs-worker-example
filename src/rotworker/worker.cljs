(ns rotworker.worker
  (:require ["rot-js/dist/rot.min.js" :as ROT]))

(js/console.log "in worker")
(js/console.log ROT)

(def api
  {"generate-map"
   (fn [w h]
     (let [m (ROT/Map.Arena. w h)]
       (.create m (fn [& result] (js/postMessage (clj->js result))))))})

(defn init []
  (js/self.addEventListener "message"
                            (fn [^js e]
                              (let [[call args] (.. e -data)]
                                (apply (get api call) args))))
  (js/console.log "worker main"))
