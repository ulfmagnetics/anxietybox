(ns anxietybox.mail
  (:require
    [environ.core :as env]
    [postal.core :as postal]
    [taoensso.timbre :as timbre]
    [taoensso.timbre.appenders.core :as appenders]
    [anxietybox.bot :as bot]
    [anxietybox.data :as data]
    [clj-http.client :as client]))

(timbre/refer-timbre)
(timbre/set-config!
 {:level :debug
  :appenders {:spit2 (appenders/spit-appender {:fname (env/env :log-file)})
              :println2 (appenders/println-appender)}
  }
 )

(defn construct-url [path]
  (str "https://" (env/env :domain-name) "/" path))

(def smtp-host "email-smtp.us-east-1.amazonaws.com")
(def smtp-port 587)
(def from-email (env/env :from-email))
(def closing (str "\n\nSincerely,\n\nYour Anxiety\n\n" (construct-url "")))

(defn postal-send
  "Send an email via Amazon SES using postal"
  [form]
  (info (merge {:from from-email} form))
  (postal/send-message {:user (env/env :smtp-username)
                        :pass (env/env :smtp-password)
                        :host smtp-host
                        :port smtp-port}
                       (merge {:from from-email} form))
)

(defn send-confirmation
  ""
  [box]
  (info box)
  (postal-send { :to (:email box)
          :subject "Confirmation requested"
          :body (str "Dear " (:name box) ",
\nYou just signed up for " (construct-url "") ".
Click here to confirm your email:
\n\t" (construct-url (str "activate/" (:confirm box))) "
\nIf you didn't sign up, ignore this email." closing)}))

(defn send-reminder [box]
  (postal-send { :to (:email box)
          :subject "Account information"
          :body (str "Dear " (:name box) ",
\nClick here to delete your account:
\n\t" construct-url(str("delete/" (:confirm box))) "
\nYou can start a new account any time." closing)}))

(defn anxiety-text [box]
  (str "Dear " (:name box) ",\n\n"
    (bot/compose
      (if (seq (:anxieties box)) (:description (rand-nth (:anxieties box))))
      (if (seq (:replies box)) (:description (rand-nth (:replies box)))))
    closing
    "\n\nP.S. Click here to delete your account:"
    "\n\t" (construct-url (str "delete/" (:confirm box)))
    "\nYou can start a new account any time."
    ))

(defn send-anxiety [box]
  (postal-send { :to (:email box)
          :subject (bot/ps)
          :body (anxiety-text box)}))


;(send-anxiety (data/box-select "ford@ftrain.com"))



