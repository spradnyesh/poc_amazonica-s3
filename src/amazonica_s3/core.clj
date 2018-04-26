(ns amazonica-s3.core
  (:require [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3t])
  (:gen-class))

(defonce cred {:access-key ""
               :secret-key ""
               :endpoint   ""})
(defonce bucket "")
(defonce static-root "")

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
