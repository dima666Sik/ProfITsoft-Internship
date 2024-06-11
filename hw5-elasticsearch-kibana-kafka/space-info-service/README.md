# Space Info

## List of Contents

- [The short description of the project](#the-short-description-of-the-project)
- [Usage](#usage)
- [Input and output data with examples (Planet entity)](#input-and-output-data-with-examples-planet-entity)
- [Input and output data with examples (Planetary System entity)](#input-and-output-data-with-examples-planetary-system-entity)
- [Author Info](#author-info)

---

> ## The short description of the project
> - To develop a Spring Boot service that stores in the database (Postgre) and provides access via the REST API to the
    data of the Planet and Planetary System entities.
> - The service must implement the endpoints that will be described below.

## Usage

1. I would you recommended to clone my project from the GitHub.
   <br> If you want to do this, please use this command:

```md  
git clone https://github.com/dima666Sik/ProfITsoft-Internship.git
```

2. To run this project, you will need to install:
    - JDK 17 or higher;
3. Then you can check performance program **by using junit tests**;
4. Also, you can start working app when executing `SpringSpaceInfoHw2Application`;
5. The result working you can check using `Postman`.
6. The file with json data will be located in the directory `resources/json-files/planets.json`

---

## Input and output data with examples (Planet entity)

1. Let's start from first task.

| Endpoint         | What doing                                                                 |
|------------------|----------------------------------------------------------------------------|
| POST /api/planet | Creates a new Planet record. Validate mandatory fields, their format, etc. |   
| {...}            |                                                                            |

### Input Data

```json
{
  "name": "Example Planet",
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

### Result

```json
{
  "message": "The planet: Example Planet with id: 2364 was created!"
}
```

2. Let's start from second task.

| Endpoint             | What doing                                                                                                                                      |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| GET /api/planet/{id} | Returns the details of the Planet record. Including the Entity 2 data it refers to, for example `planetarySystem`: {`id`: 2, `name`: `MyValue`} |   

### Input Data

```http request
GET /api/planet/{2364}
```

### Result

```json
{
  "id": 2364,
  "name": "Planet61",
  "hasRings": false,
  "hasMoons": false,
  "atmosphericComposition": "Hydrogen, Helium",
  "massDto": {
    "unit": "KILOGRAM",
    "value": 5.565711542766461
  },
  "diameterDto": {
    "unit": "KILOMETER",
    "value": 5901.0
  },
  "planetarySystemResponseDto": {
    "id": 1,
    "name": "Planetary System X1"
  }
}
```

3. Let's start from third task.

| Endpoint             | What doing                                                                   |
|----------------------|------------------------------------------------------------------------------|
| PUT /api/planet/{id} | Updates the data of one Planet record by ID. Do not forget about validation. |   
| {...}                |                                                                              |

### Input Data

```json
{
  "name": "Updated Planet",
  "hasRings": false,
  "hasMoons": true,
  "atmosphericComposition": "Xz Nitrogen, Oxygen",
  "mass": {
    "value": 50.972E24,
    "unit": "KILOGRAM"
  },
  "diameter": {
    "value": 112742,
    "unit": "KILOMETER"
  }
}
```

### Result

```json
{
  "message": "The planet: Example Planet with id: 2364 was updated!"
}
```

4. Let's start from forth task.

| Endpoint                | What doing                  |
|-------------------------|-----------------------------|
| DELETE /api/planet/{id} | Deletes Planet record by ID |   

### Input Data

```http request
DELETE /api/planet/{2364}
```

### Result

```json
{
  "message": "The planet by id:2364 was deleted!"
}
```

5. Let's start from fifth task.

| Endpoint                                         | What doing                                                                                                                                                                                                                                                                                                                                                     |
|--------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST /api/planet/_list                           | Returns a data structure that has a list of Entity 1 elements that match the requested page and the total number of pages. Example:                                                                                                                                                                                                                            |   
| {`planetarySystemId`: 2, …,`page`: 1,`size`: 20} | {`list`: [...],`totalPages`: 5 }Entries in the array will have a reduced set of Entity 1 fields (because not all fields are needed in list mode). In the request, you can optionally specify fields (2-3 fields) by which you can filter records (entity2, etc.). Filtering should be at the level of the request to the database! Don't forget about indexes! |

### Input Data

```json
{
  "idPlanetSystem": "0",
  "namePlanetSystem": "Planetary System X0",
  "page": "0",
  "size": "3"
}
```

### Result

```json
{
  "list": [
    {
      "id": 2366,
      "name": "Planet421",
      "hasRings": false,
      "hasMoons": false,
      "atmosphericComposition": "Oxygen, Nitrogen, Carbon dioxide",
      "massDto": {
        "unit": "KILOGRAM",
        "value": 8.82736772071069
      },
      "diameterDto": {
        "unit": "KILOMETER",
        "value": 5900.0
      },
      "planetarySystemResponseDto": {
        "id": 0,
        "name": "Planetary System X0"
      }
    },
    {
      "id": 2368,
      "name": "Planet679",
      "hasRings": true,
      "hasMoons": false,
      "atmosphericComposition": "Magnesium",
      "massDto": {
        "unit": "KILOGRAM",
        "value": 4.023483844759402
      },
      "diameterDto": {
        "unit": "KILOMETER",
        "value": 9025.0
      },
      "planetarySystemResponseDto": {
        "id": 0,
        "name": "Planetary System X0"
      }
    },
    {
      "id": 2373,
      "name": "Planet335",
      "hasRings": true,
      "hasMoons": true,
      "atmosphericComposition": "Nitrogen, Carbon dioxide",
      "massDto": {
        "unit": "KILOGRAM",
        "value": 2.813621386029832
      },
      "diameterDto": {
        "unit": "KILOMETER",
        "value": 7841.0
      },
      "planetarySystemResponseDto": {
        "id": 0,
        "name": "Planetary System X0"
      }
    }
  ],
  "totalPages": 3
}
```
5. Let's start from fifth task.

| Endpoint                     | What doing                                                                                                                                                                                                                                                                                                                                                           |
|------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST /api/planet/_report     | Returns a data structure that has a list of Entity 1 elements that match the requested page and the total number of pages. Example:                                                                                                                                                                                                                                  |   
| {`planetarySystemId`: 2, … } | {`list`: [...],`totalPages`: 5 }Entries in the array will have a reduced set of Planet fields (because not all fields are needed in list mode). In the request, you can optionally specify fields (2-3 fields) by which you can filter records (PlanetarySystem, etc.). Filtering should be at the level of the request to the database! Don't forget about indexes! |

### Input Data

```json
{
  "idPlanetSystem":"0",
  "pageSize":"5",
  "fileFormat":"CSV"
}
```

### Result (file)

```cvs
"idPlanet","namePlanet","hasPlanetRings","hasPlanetMoons","atmosphericCompositionPlanet","massPlanet","diameterPlanet","planetarySystem"
"2364","Planet61","false","false","Hydrogen, Helium","Mass(super=SIBasicPhysicalQuantity(value=5.565711542766461), id=2364, unit=KILOGRAM)","Diameter(super=SIBasicPhysicalQuantity(value=5901.0), id=2364, unit=KILOMETER)","PlanetarySystem(id=1, name=Planetary System X1)"
...
"2380","Planet14","false","false","Oxygen, Nitrogen, Carbon dioxide","Mass(super=SIBasicPhysicalQuantity(value=2.365481399805257), id=2380, unit=KILOGRAM)","Diameter(super=SIBasicPhysicalQuantity(value=5265.0), id=2380, unit=KILOMETER)","PlanetarySystem(id=0, name=Planetary System X0)"
"2381","Planet884","true","false","Hydrogen, Helium","Mass(super=SIBasicPhysicalQuantity(value=6.20876867341564), id=2381, unit=KILOGRAM)","Diameter(super=SIBasicPhysicalQuantity(value=7964.0), id=2381, unit=KILOMETER)","PlanetarySystem(id=1, name=Planetary System X1)"
```

6. Let's start from sixth task.

| Endpoint                | What doing                                                                                                                                                                            |
|-------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST /api/planet/upload | Accepts the JSON file for which the parser was developed in Task 1 (the format can be adapted). Stores all valid records from this file in the database.                              |   
| file(`planets.json`)    | In response, it generates a JSON that indicates the number of successfully imported records, as well as unsuccessful ones (for example, no matching PlanetarySystem record was found) |
### Result

```json
{
  "numberOfSuccessfulExecutions": 18,
  "numberOfUnsuccessfulExecutions": 12
}
```
## Input and output data with examples (Planetary System entity)
1. Let's start from first task.

| Endpoint                   | What doing                                                                                                      |
|----------------------------|-----------------------------------------------------------------------------------------------------------------|
| POST /api/planetary-system | Adds an Essence 2 entry. Have control over the uniqueness of Entity 2 names (do not allow to create duplicates) |   
| {...}                      |                                                                                                                 |

### Input Data

```json
{
  "name": "Planetary System X14"
}
```

### Result

```json
{
  "message": "The planetary system:Planetary System X14 with id: 12 was created!"
}
```

2. Let's start from second task.

| Endpoint                  | What doing                                                              |
|---------------------------|-------------------------------------------------------------------------|
| GET /api/planetary-system | Returns a list of all PlanetarySystem records available in the database |

### Result

```json
[
  {
    "id": 0,
    "name": "Planetary System X0"
  },
  {
    "id": 1,
    "name": "Planetary System X1"
  }
]
```

3. Let's start from third task.

| Endpoint                  | What doing                                                                                         |
|---------------------------|----------------------------------------------------------------------------------------------------|
| PUT /api/planetary-system | Changes PlanetarySystem record by ID. Given the control of the uniqueness of PlanetarySystem names |   
| {...}                     |                                                                                                    |

### Input Data

```json
{
  "name": "Planetary System X111"
}
```

### Result

```json
{
  "message": "The planetary system:Planetary System X111 by id: 12 was updated!"
}
```

4. Let's start from forth task.

| Endpoint                          | What doing                           |
|-----------------------------------|--------------------------------------|
| DELETE /api/planetary-system/{id} | Deletes PlanetarySystem record by ID |

### Result

```json
{
  "message": "The planetary system by id: 12 was deleted!"
}
```




## Author Info

- [Linkedin](https://www.linkedin.com/in/dmytro-kohol-333a7a2aa/)

- [My GitHub](https://github.com/dima666Sik)

- [ProfITsoft-Internship repository](https://github.com/dima666Sik/ProfITsoft-Internship)

[Back To The Top](#usage)