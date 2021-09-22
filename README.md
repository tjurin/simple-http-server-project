# INSTRUCTIONS
The goal with this assignment is to implement a simple HTTP server in Java using only built-in SDK features (only exception is using an object mapper such as Jackson).

**NB** Dont use the `jdk.httpserver` module, you should parse the HTTP request and responses manually.

## Requirements
* Multi-threaded request handling
* Support for static resources, fx frontend assets
* Support subset of HTTP (<= HTTP/1.1)
    * `GET` and `POST` requests.
    * Query parameters for `GET` and `POST` requests.
    * Support JSON in response body.
* Web controller classes support (manual wiring/object instantiation is OK).

With these requirements full-filled you should be able to serve the web app `my-web-project` (require support for static resources), and use it (depending on how far you get with the REST support).

We have added some classes to get you started, but you basically just need to implement `SimpleWebServer`.

**NB** We don't expect you to spend more than a few hours on this and if you get stuck it does not necessarily mean that you are not qualified to work with us. Drop us a line by mail and we will give you a hint.

## Bonus requirements
Absolutely not necessary, but if you think this is fun and wanna keep going we suggest you add:

* Path parameter support
* Support JSON request body
* Access to raw request object as method parameter.
