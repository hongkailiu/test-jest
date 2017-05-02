# test-jest
[![Build Status](https://travis-ci.org/hongkailiu/test-jest.svg?branch=master)](https://travis-ci.org/hongkailiu/test-jest)
[![Coverage Status](https://coveralls.io/repos/github/hongkailiu/test-jest/badge.svg?branch=master)](https://coveralls.io/github/hongkailiu/test-jest?branch=master)
[![Release](https://jitpack.io/v/hongkailiu/test-jest.svg)](https://jitpack.io/#hongkailiu/test-jest)

## Run test locally
- Need a running Elastic instance, eg,

    ```sh
    docker run -d --name some-elastic -p 9200:9200 -p 9300:9300 elasticsearch:5.3.1
    ```

- The url of the Elastic instance is specified by the value _elasticUrl_
in _app.json_. By default, it is _http://127.0.0.1:9200_.

- Run test

    ```sh
    ./gradlew clean check
    ```