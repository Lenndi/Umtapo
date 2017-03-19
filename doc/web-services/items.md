# Item

## GET items/:id

Return item.

### URL Params

#### Required

  `id=[integer]` Item id in database.

### Success Response

  * **Code:** 200 <br>
    **Content:**
```javascript
     {
        id : 1,
        internalId: 3545,
        type: "book",
        shelfmark: {
          field1: "LEH",
          field2: "MAU",
          field3: "NOU"},
        condition: "GOOD",
        isLoanable : true,
        purchasePrice: 12,
        currency: "€",
        library : 2,
        recordId: "FRB78979879878789"
      }
```
 
### Error Response

  * **Code:** 404 NOT FOUND <br>
    **Content:** `{ error : "Item does not exist" }`

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

## POST items

Create item.

### Data Params

#### Required

  `internalId=[integer]` Internal id.

  `type=[string]` Item type define in a list.

  `shelfmark=[Shelfmark]` Shelfmark.

  `condition=[string]` Item condition define in a list.

  `isLoanable=[boolean]` Define if the item can be loan or not.

  `purchasePrice=[number]` Item purchase price.

  `currency=[string]` Currency when the item was purchase.

  `library=[integer]` Library id the item belongs to.

  `recordId=[string]` Id of the corresponding record.

### Success Response

  * **Code:** 201 <br>
    **Content:**
```javascript
      {
        id : 1,
        internalId: 3545,
        type: "book",
        shelfmark: {
          field1: "LEH",
          field2: "MAU",
          field3: "NOU"},
        condition: "GOOD",
        isLoanable : true,
        purchasePrice: 12,
        currency: "€",
        library : 2,
        recordId: "FRB78979879878789"
  }
```
 
### Error Response

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`
