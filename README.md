# gh-commits-category

[![Build Status](https://travis-ci.com/rafaeldelboni/gh-commits-category.svg?branch=master)](https://travis-ci.com/rafaeldelboni/gh-commits-category)

#### Consolidates [Semantic Commit Messages](https://seesparkbox.com/foundry/semantic_commit_messages) in Github per user

## Built With

* [Clojure](https://clojure.org/)
* [Leiningen](https://leiningen.org/)
* [compojure-api](https://github.com/metosin/compojure-api)

## Getting Started

These instructions will get the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install to run this project

* [Java](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [Clojure](https://clojure.org/guides/getting_started)
* [Leiningen](https://leiningen.org/)

### Create your profiles.clj file

Based on `profiles.clj.sample` file create your own `profiles.clj`, you will need an [Github Api Key](https://help.github.com/en/articles/creating-a-personal-access-token-for-the-command-line) for this step.

### Restore dependencies
```
lein deps
```

### Run the application

```
lein run
```
This command should start a server on [http://localhost:8080](http://localhost:8080).

## Running the tests

To run unity the tests
```
lein test
```
