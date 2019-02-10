# AutoScout24 Car Advert Backend Service

### Running

You need to download and install sbt for this application to run.

Once you have sbt installed, the following at the command prompt will start up Play in development mode:

```bash
sbt run
```

Play will start up on the HTTP port at <http://localhost:9000/>.   You don't need to deploy or reload anything -- changing any source code while the server is running will automatically recompile and hot-reload the application on the next HTTP request. 

### Usage

The service will start with an in memory list of 5 Adverts which is not persistent.
This can be found in the CarAdvertRepository. In the feature, this can be replaced by a persistence storage such as RDBMS.

Please see below quick usage:

* Get the list of all adverts sorted by Id by default

```bash
curl --include http://localhost:9000/api/v1/adverts
```

* Get the list of all adverts sorted by a query parameter, for example: title

```bash
curl --include http://localhost:9000/api/v1/adverts?sortBy=title
```

* Get the data for a single advert by Id for example id = 2

```bash
curl --include http://localhost:9000/api/v1/adverts/2
```

* Create a new car advert via a Json data

```bash
curl --include -d '{"id": 7, "title": "Jaguar","fuel": "diesel","price": 0,"isNew": true}' -H "Content-Type: application/json" -X POST http://localhost:9000/api/v1/advert
 
```

* Update an existing car advert via a Json data

```bash
curl --include -d '{"id": 3, "title": "Range Rover","fuel": "diesel","price": 0,"isNew": true}' -H "Content-Type: application/json" -X PUT http://localhost:9000/api/v1/advert
```

* Delete an existing car Advert by Id for example id = 3

```bash
curl --include -X DELETE http://localhost:9000/api/v1/advert/3
```

