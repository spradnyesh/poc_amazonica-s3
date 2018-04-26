(ns amazonica-s3.core
  (:require [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3t])
  (:gen-class))

;; TODO: populate keys from env
(defonce cred {:access-key ""
               :secret-key ""
               :endpoint   ""}) ; region, eg "ap-south-1"
(defonce bucket "")
(defonce static-root "")

;;;; steps to host static website
;; - create-bucket
;; - steps to be done on AWS console
;;   - properties -> static website hosting -> "use this bucket to host a website ..."
;;   - upload empty index.html and error.html
;;   - http://awspolicygen.s3.amazonaws.com/policygen.html (http://g14n.info/2016/04/s3-bucket-public-by-default/)
;;     - permissions -> bucket policy -> add
;; - put-object
;; - url to access object: https://s3.<region>.amazonaws.com/<bucket-name>/<file-name>.<extn>

(defn create-bucket [nm]
  (s3/create-bucket cred nm))

(defn put-object [path]
  (let [content (clojure.java.io/file (str static-root path))]
    (if-not (.exists content)
      (println path "does not exist")
      (s3/put-object cred
                     :bucket-name bucket
                     :key path
                     :file content))))

(defn get-object [k]
  (s3/get-object cred
                 :bucket-name bucket
                 :key k))
