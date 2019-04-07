(ns gh-commits-category.logic-test
  (:require [clojure.test :refer :all :as t]
            [gh-commits-category.logic :as l]))

(def sample-commits
  [{:node
    {:defaultBranchRef
     {:target
      {:history
       {:edges [{:node {:message "feat: bla bla"}}
                {:node {:message "fix: ble ble"}}
                {:node {:message "refac: bli bli"}}]}}}}}
   {:node
    {:defaultBranchRef
     {:target
      {:history
       {:edges [{:node {:message "test: blo blo"}}
                {:node {:message "docs: blu blu"}}]}}}}}
   {:node
    {:defaultBranchRef
     {:target
      {:history
       {:edges [{:node {:message "initial commit"}}
                {:node {:message "chore: chop chop"}}
                {:node {:message "style: barara bururu"}}
                {:node {:message "fixes rompa lompa"}}
                {:node {:message "refactoring ziriduns"}}
                {:node {:message "adds chub dub"}}]}}}}}])

(t/deftest split-commit-test
  (t/testing "should split commit message by : or show full message"
    (t/is (= (l/split-commit "feat: dasgurt") "feat"))
    (t/is (= (l/split-commit "fix: dasgurt") "fix"))
    (t/is (= (l/split-commit "a: dasgurt") "a"))
    (t/is (= (l/split-commit "feat dasgurt") "feat dasgurt"))))

(t/deftest commit->category-key-test
  (t/testing "should convert the commit into a category key"
    (t/is (= (l/commit->category-key "Feat") :feat))
    (t/is (= (l/commit->category-key "Fix") :fix))
    (t/is (= (l/commit->category-key "Refactor") :refac))
    (t/is (= (l/commit->category-key "Tests") :test))
    (t/is (= (l/commit->category-key "CHORE") :chore))
    (t/is (= (l/commit->category-key "Docs") :docs))
    (t/is (= (l/commit->category-key "Styles") :style))
    (t/is (= (l/commit->category-key "Added some barara") :feat))
    (t/is (= (l/commit->category-key "Updated some bururu") :fix))
    (t/is (= (l/commit->category-key "initial commit") :other))))

(t/deftest commits->category-key-map-test
  (t/testing "should convert the complex github obj into a key vec"
    (t/is (= (l/commits->category-key-map sample-commits)
             [:feat
              :fix
              :refac
              :test
              :docs
              :other
              :chore
              :style
              :fix
              :refac
              :feat]))))

(t/deftest category-count-test
  (t/testing "should convert the complex github obj into a str vec"
    (t/is (= (l/category-count (l/commits->category-key-map sample-commits))
             {:feat 2 :fix 2 :refac 2 :test 1 :docs 1 :other 1 :chore 1 :style 1}))))
