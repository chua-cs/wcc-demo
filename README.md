# Demo Project for WCC

---

Java 17, Spring Boot, Maven, H2 In-Memory DB

To build: `mvn clean install;`

Followed by: `mvn spring-boot:run`.

Note: This application initialize the H2 DB with full UK postcode dataset. Shall take roughly 2-3mins.

Once done, you may refer to below section to play around with the API.

---
List of available postcodes can be obtained here: [UK-Postcode](https://ukpostcode.org/)

Postman Collection script is available at: /postman

Sample: [link](http://localhost:8085/demo/api/v1/public/distance?postcode_1=AL5%203NG&postcode_2=CF14%200LB).

Output:
```json
{
  "message":"The distance between two locations is 193.208KM.",
  "error":false,
  "data":{
    "distance":193.2079939006983,
    "unit":"kilometer",
    "locations":[
      {
        "postcode":"AL5 3NG",
        "latitude":51.832128,
        "longitude":-0.383008
      },
      {
        "postcode":"CF14 0LB",
        "latitude":51.561669,
        "longitude":-3.152346
      }
    ]
  }
}
```
