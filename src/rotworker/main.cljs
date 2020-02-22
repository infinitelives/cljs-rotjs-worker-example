(ns rotworker.main)

(defn init []
  (let [worker (js/Worker. "js/worker.js")]
    (.addEventListener worker "message" (fn [e] (js/console.log e)))
    (.postMessage worker "hello world")))
