# Gamification WP 2

Adrien Allemand, Vincent Guidoux, Guillaume Hochet et Loyse Krug

# Build and run the WP2

We already build a .jar for you, so to use our repo, you just have to 

```
git clone https://github.com/Nortalle/WP2.git
cd WP2
docker-compose up
```

You can then access:

* the [API documentation](http://localhost:8080/api/swagger-ui.html), generated from annotations in the code
* the [API endpoint](http://localhost:8080/api/), accepting GET and POST requests
* at the [UNIX Mac/Linux Docker for windows](http://localhost:8080/api/) or [Docker toolbox](http://192.168.99.100:8080/api/)
* or in command line with `curl`

You can use curl to invoke the endpoints:

* To retrieve the list of badges previously created :

```
curl -X GET --header 'Accept: application/json' --header 'authorization: <apiKey>' 'http://localhost:8080/api/badges'
```

* To create a new badge (beware that in the live documentation, there are extra \ line separators in the JSON payload that cause issues in some shells)

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'authorization: <apiKey>' -d '{ \ 
   "image": "string", \ 
   "name": "string", \ 
   "visible": false \ 
 }' 'http://localhost:8080/api/badges'
```

- To delete a badges previously created :

```
curl -X DELETE --header 'Accept: application/json' --header 'authorization: >apiKey>' 'http://localhost:8080/api/badges/<badgeId>'
```

- To get a specified badge already created :

```
curl -X GET --header 'Accept: application/json' --header 'authorization: <apiKey>' 'http://localhost:8080/api/badges/<badgeId>'
```

- To modified a specified badge already created

```
curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'authorization: <apiKey>' -d '{ \ 
   "image": "newValue", \ 
   "name": "newValue", \ 
   "visible": true \ 
 }' 'http://localhost:8080/api/badges/<badgeId>'
```

Do the same for the pointScales and the rules, here what these endpoints send and receive

Post, Put:

```json
rule :
{
  "ifEventType": "string",
  "ifPropertyCondition": "string",
  "ifPropertyName": "string",
  "thenAwardPoint": 0,
  "thenBadgeId": 0,
  "thenPointScaleId": 0
}
pointScale : 
{
  "description": "string",
  "name": "string"
}
```

Get :

```
rule :
{
  "id": 0,
  "ifEventType": "string",
  "ifPropertyCondition": "string",
  "ifPropertyName": "string",
  "thenAwardPoint": 0,
  "thenBadgeId": 0,
  "thenPointScaleId": 0
}
pointScale : 
{
  "id": 0,
  "description": "string",
  "name": "string"
}
```



# Test the Gamification microservice by running the executable specification

You can use the Cucumber project to validate the API implementation. Do this when the server is running.

```
cd cd swagger/fruits-specs/
mvn clean test
```
You will see the test results in the console, but you can also open the file located in `./target/cucumber/index.html`

