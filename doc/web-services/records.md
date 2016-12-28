# Records

## GET records

Return a bibliographic record based on ISBN number.

### URL Params

#### Required

  _At least one parameter is required._

  `isbn=[string]` Search based on ISBN number, it could be ISBN-10 or ISBN-13.

  `title=[string]` Search based on item title.

### Filters

  `result_size=[number]` Number of records to return (10 by default).

  `page=[number]` Page to return (1 by default).

### Success Response

  * **Code:** 200 <br>
    **Content:** json MODS format
  * **Code:** 204 <br>
    **Content:** `{ message : "No record found" }`
 
### Error Response

  * **Code:** 503 SERVICE UNAVAILABLE <br>
    **Content:** `{ error : "We can't contact Z39.50 provider" }`
