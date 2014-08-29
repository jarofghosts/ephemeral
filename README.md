# ephemeral

[![Build Status](http://img.shields.io/travis/jarofghosts/ephemeral.svg?style=flat)](https://travis-ci.org/jarofghosts/ephemeral)

self-destructing messages

## what?

post messages with expiration rules. example!

```shell
curl -X PUT http://localhost:3000/message/ --data '{"message": "lol", "views": 10}'
```

will return a uuid. if you were to then hit `http://localhost:3000/message/<id>`,
you would see your message. if you did that 10 times, the 11th time you would
get a 404. why? because.

## prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## running

To start a web server for the application, run:

    lein ring server

## license

MIT
