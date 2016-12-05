## GET libraries/:id

Return library informations.

### URL Params

#### Required
 
   `id=[integer]` Library id in database.

#### Data Params

  None

### Success Response

  * **Code:** 200 <br>
    **Content:** `{
        id : 1,
        name : "My village library",
        shelfMarkNb : 3,
        useDeweyClassification : true,
        subscriptionDuration : 365,
        borrowDuration : 30,
        currency : "€"}`
 
### Error Response

  * **Code:** 404 NOT FOUND <br>
    **Content:** `{ error : "Library does not exist" }`
