(ns rotworker.main)

(defn init []
  (let [worker (js/Worker. "js/worker.js")]
    (.addEventListener worker "message" (fn [e] (js/console.log "result" (.-data e))))
    (.postMessage worker (clj->js ["generate-map" [10 10]]))))
