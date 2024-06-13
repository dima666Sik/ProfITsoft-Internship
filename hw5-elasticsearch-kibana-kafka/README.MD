# HW5 ELASTICSEARCH KIBANA KAFKA

If you want to start project please firstly use command `docker compose up`.<br>
And secondly local start 2 microservices. 
In the docker-compose command to create container with these 
microservices was commented out because issues.

You can send `http://localhost:8080/api/planet`
and body can be like that:
```json
{
  "name": "Example Planet777",
  "hasRings": false,
  "hasMoons": true,
  "atmosphericComposition": "Nitrogen, Oxygen",
  "mass": {
    "value": 5.972E24,
    "unit": "KILOGRAM"
  },
  "diameter": {
    "value": 12742,
    "unit": "KILOMETER"
  }
}

```

to response, you can get that:
```json
{
    "message": "The planet: Example Planet777 with id: 4 was created!"
}
```
And the elasticsearch save this planet and info about planet will be sent to the "admin". 

