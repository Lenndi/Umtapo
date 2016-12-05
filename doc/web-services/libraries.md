# Libray

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


# Borrower

## POST borrower

Create borrower.

### URL Params

#### Required

#### Data Params

`name=[String]` Borrower name.
`comment=[String]` Borrower comment.
`birthday=[Calendar]` Borrower birthday.
`quota=[Integer]` Max document borrower can loan.
`emailOptin=[Boolean]` ??.
`address=[Address]` Borrower address informations.
`subscription=[Subscription]` Borrower subscription informations.
`loan=[List<Loan>]` List of loan documents.

### Success Response

  * **Code:** 201 <br>
    **Content:** `{
TODO

### Error Response

TODO
