MBTA Time2Go Estimator
----

Check out the [demo](http://mbta-time2go.elasticbeanstalk.com/)

Gives an estimated time to leave to a station based on your current geolocation.

Merges MBTA realtime data API with Google's direction API. Uses Java's CompletableFuture's to get the realtime data in parallel with Google's direction request.

Uses:

* Spring MVC
* Backbone.js
* Java Streams
* Java Completable Futures
* HTML5 Geolocation API