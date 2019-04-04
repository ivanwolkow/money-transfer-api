# Money transfer app

## Introduction

This Guice-based app is made for the demonstration purpose and provides several API methods 
which allows multiple users to transfer money between accounts as well as to observe the state of the accounts.

The accounts are stored in-memory and are initialized during the application startup.
Initial state of the accounts is as follows:

| Account id | Amount (Balance) |
| ---------- | ---------------- |
| 1 | 1000 |
| 2 | 2000 |
| 3 | 3000 |
| 4 | 4000 |
| 5 | 5000 |

Due to the simplicity requirements for this demo-application, there is no support for adding/deleting accounts,
authentication, validation of the input parameters etc.

There are tests in **TransferControllerTest** which demonstrates that the API works as expected.


## Build instructions

`mvn clean install`

## How to run

`java -jar target/money-transfer-api-1.0-SNAPSHOT.jar server`

# Exposed API

Let's export the server endpoint to the shell variable **API_ENDPOINT**.
Example:  
`export API_ENDPOINT=http://127.0.0.1:8080`

### List all accounts

`GET /accounts`

*Shell script example:*

```shell
curl -X GET \
-H "Content-Type: application/json" \
$API_ENDPOINT/accounts
```

### Show a single account by its ID

`GET /accounts/{id}`

*Shell script example:*

```shell
curl -X GET \
-H "Content-Type: application/json" \
$API_ENDPOINT/accounts/2
```

### Transfer money between accounts

`POST /transfer`

The POST request must contain a JSON body with the transfer details as shown in the example below.

*Shell script example:*

```shell
curl -X POST \
-H "Content-Type: application/json" \
-d '{"srcAccount":1, "dstAccount":2, "amount":5.0}' \
$API_ENDPOINT/transfer
```
