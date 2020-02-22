(ns rotworker.worker
  (:require ["rot-js/dist/rot.min.js" :as ROT]))

(js/console.log "in worker")
(js/console.log ROT)

(defn init []
  (js/self.addEventListener "message"
                            (fn [^js e]
                              (js/postMessage (.. e -data))))
  (js/console.log "worker main"))
