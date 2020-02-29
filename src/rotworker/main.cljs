(ns rotworker.main)

(def callbacks (atom {}))

(defonce worker
 (let [w (js/Worker. "js/worker.js")]
   (.addEventListener w "message"
                       (fn [e]
                         (let [[uid result] (.-data e)]
                           (when (@callbacks uid)
                             ((@callbacks uid) (js->clj result))
                             (swap! callbacks dissoc uid)))))
   w))

(defn make-uid []
  (-> (js/Math.random) str (.split ".") .pop))

(defn rpc [call args callback]
  (let [uid (make-uid)]
    (swap! callbacks assoc uid callback)
    (.postMessage worker (clj->js [uid call args]))))

(defn init [])

(js/console.log "Triggering map generation")
(rpc "generate-cellular-map" [1024 1024 0.5 {:connected true}] (fn [m] (js/console.log "got map:" (clj->js m)))) 

