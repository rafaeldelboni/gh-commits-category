(ns gh-commits-category.logic
  (:require [clojure.string :as str]))

(defn split-commit [commit]
  (first (str/split commit #":")))

(defn commit->category-key [c]
  (let [lc (str/lower-case c)]
    (cond
      (str/includes? lc "feat") :feat
      (str/includes? lc "fix") :fix
      (str/includes? lc "refac") :refac
      (str/includes? lc "test") :test
      (str/includes? lc "chore") :chore
      (str/includes? lc "docs") :docs
      (str/includes? lc "style") :style
      (str/includes? lc "add") :feat
      (str/includes? lc "update") :fix
      :else :other)))

(defn commits->category-key-map [user-commits]
  "Flattens complex github data object and extract commit categories into vec"
  (map #(commit->category-key
          (split-commit
            (get-in % [:node :message])))
       (reduce into []
               (map #(get-in % [:node :defaultBranchRef :target :history :edges])
                    user-commits))))

(defn category-count [commits]
  "Counts occurence of each commit type"
  (reduce
    (fn [acum key]
      (update-in acum [key] (fnil inc 0)))
  {}
  commits))

(def commits->category-map (comp category-count commits->category-key-map))
