## AppDirect Coding Assignment

* Created an application called BookService which allows users to do CRUD operation on books.

* Integrated with following AppDirect APIs:

  a. Subscription Create

  b. Subscription Cancel

  c. Subscription Change

## Libraries Used:

* Apache CXF (http://cxf.apache.org/)
* Signpost - For signing URL with Oauth credentials (https://github.com/mttkay/signpost)
* Jackson - For JSON Serialization and deserialization (https://github.com/FasterXML/jackson)
* Apache HTTP Client for sending request to AppDirect for fetching notification data (http://hc.apache.org/httpclient-3.x/)
* Apache Maven for building the source code (https://maven.apache.org/)

## How to run the application:

* The main class in the application is server.AppServer. Make sure to change the IP address to host the application.
* OAuth Credentials have to be put in the security.properties file in the src/main/resources folder.
