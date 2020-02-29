(ns rotworker.worker
  (:require ["rot-js/dist/rot.min.js" :as ROT]))

(js/console.log "in worker")
(js/console.log ROT)

(def api
  {"generate-cellular-map"
   (fn [uid [w h r opts]]
     ; TODO: seed
     (let [m (ROT/Map.Cellular. w h opts)
           map-shape (make-array nil w h)]
       (.randomize m r)
       ;(.create m js/console.log)
       (.create m (partial aset map-shape))
       (js/postMessage (clj->js [uid map-shape]))))})

(defn init []
  (js/self.addEventListener "message"
                            (fn [^js e]
                              (let [[uid call args] (.. e -data)]
                                ((get api call) uid args))))
  (js/console.log "worker main"))
