(ns gh-commits-category.schemas
  (:require [schema.core :as s]))

(s/defschema Categories
  {(s/optional-key :fix) s/Num
   (s/optional-key :feat) s/Num
   (s/optional-key :chore) s/Num
   (s/optional-key :docs) s/Num
   (s/optional-key :refac) s/Num
   (s/optional-key :other) s/Num
   (s/optional-key :test) s/Num
   (s/optional-key :merge) s/Num
   (s/optional-key :style) s/Num})
