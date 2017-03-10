## GET borrowers

Return borrowers.

### URL params

#### Optional

`name=[string]` Search borrowers with name containing term.

`id=[number]` Search borrowers by id.

### Filters

`page=[number]` Page to return.

`size=[number]` Number of borrowers per page.

`sort=[id|email|name|city|endSubscription|quota|lateness]` Sort borrowers by term (ie: id).

`order=[desc|asc]` Order the sort parameter - asc by default.

`email=[string]` Filter borrowers with email containing term.

`name=[string]` Filter borrowers with name containing term.

`city=[string]` Filter borrowers with city containing term.

### Success Response

  * **Code:** 200 <br>
    **Content:**
```javascript
{
  "content": [
    {
      "idIdentify": 1,
      "id": 1,
      "name": "Michel Test",
      "comment": null,
      "birthday": "2016-11-30T01:00:00+01:00",
      "quota": 3,
      "emailOptin": true,
      "address": {
        "idIdentify": 2,
        "id": 1,
        "address1": "3 rue des Poules",
        "address2": "Étage 2",
        "zip": "72000",
        "city": "Le Mans",
        "phone": "0243813894",
        "email": "michel.test@test.com"
      },
      "subscriptions": null,
      "library": null
    },
    {
      "idIdentify": 3,
      "id": 2,
      "name": "Michel Test",
      "comment": null,
      "birthday": "2016-11-30T01:00:00+01:00",
      "quota": 3,
      "emailOptin": true,
      "address": {
        "idIdentify": 4,
        "id": 2,
        "address1": "3 rue des Poules",
        "address2": "Étage 2",
        "zip": "72000",
        "city": "Le Mans",
        "phone": "0243813894",
        "email": "michel.test@test.com"
      },
      "subscriptions": null,
      "library": null
    }
  ],
  "last": false,
  "totalPages": 2,
  "totalElements": 4,
  "sort": [
    {
      "direction": "ASC",
      "property": "id",
      "ignoreCase": false,
      "nullHandling": "NATIVE",
      "ascending": true
    }
  ],
  "numberOfElements": 2,
  "first": true,
  "size": 2,
  "number": 0
}
```
  * **Code:** 204 <br>
    **Content:** `{ message : "No record found" }`

### Error Response

  * **Code:** 503 SERVICE UNAVAILABLE <br>

## GET borrowers/:id

Return a borrower by id.

### URL Params

#### Required

id=[integer] borrower id in database.

### Success Response

  * **Code:** 200 <br>
    **Content:**
```javascript
{
  "idIdentify": 1,
  "id": 1,
  "name": "Michel Test",
  "comment": null,
  "birthday": "2016-11-30T00:00:00Z",
  "quota": 3,
  "emailOptin": true,
  "address": {
    "idIdentify": 2,
    "id": 1,
    "address1": "3 rue des Poules",
    "address2": "Étage 2",
    "zip": "72000",
    "city": "Le Mans",
    "phone": "0243813894",
    "email": "michel.test@test.com"
  },
  "subscriptions": [
    {
      "idIdentify": 3,
      "id": 1,
      "start": "2016-12-06T00:00:00Z",
      "end": "2017-12-25T00:00:00Z",
      "contribution": 32,
      "borrower": null,
      "library": null
    }
  ],
  "library": null
}
```
  * **Code:** 204 <br>
    **Content:** `{ message : "No record found" }`

### Error Response

  * **Code:** 503 SERVICE UNAVAILABLE <br>
