(ns anxietybox.worker
  (:require
    [anxietybox.data :as data]
    [anxietybox.bot :as bot]
    [anxietybox.mail :as mail]
    [taoensso.timbre :as timbre]
    [taoensso.timbre.appenders.core :as appenders]))

;; Logging prefix
(timbre/refer-timbre)
(timbre/set-config!
 {:level :debug
  :appenders {:println2 (appenders/println-appender)}
  }
 )

(defn send-email []
  (let [boxes (data/boxes-for-update)]
    (doseq [box boxes]
      (info (str "Sending anxiety -> id: " (:id box) ", email: " (:email box)))
      (mail/send-anxiety box))))

(defn -main []
  (send-email))