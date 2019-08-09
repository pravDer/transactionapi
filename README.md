# transactionApp

A simple dropwizrd application which exposes REST end points to do money transfers.


## How to start the transactionApp application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/transaction-api-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

## Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

## Available REST End Points

|  REST API Path                                    |  Description                                   |  Request               |  Response        |
| --------------------------------------------------|------------------------------------------------|------------------------| -----------------|
|    GET     /v1/account/{accountId}                |  Get account details                           | PathParam "acct_123"   | [ { "accountid": "acct_123","name": "John","money": { "balance": 0.99, "currency": "USD" } } ]  |
|    GET     /v1/account/{accountId}/balances       |  Get account balances                          | PathParam "acct_123"   | [ { "accountid": "acct_123","name": "John","money": { "balance": 0.99, "currency": "USD" } } ]  |
|    GET     /v1/account/{accountId}/transactions   |  Get transactions for an account               | PathParam "acct_123"   |  []              |
|    POST    /v1/transaction/deposit-intent         |  Put a deposit intent                          | {"accountNumber" : "acct_123", "money" :{"balance":"1000.0","currency":"USD"}}  | "unique_token" |
|    PUT     /v1/transaction/deposit-intent/commit  |  Commit the deposit intent                     | "unique_token"         |  HTTP Status "Ok" |
|    POST    /v1/transaction/transfer-intent        |  Put a transfer intent                         | { "toAccountNumber":"123", "fromAccountNumber":"456", "money" : { "balance":"1000.0", "currency":"USD" } }  | "unique_token" |
|    PUT     /v1/transaction/transfer-intent/commit |  Commit the transfer intent                    |  "unique_token"       |   HTTP Status "Ok" |
|    POST    /v1/transaction/withdraw-intent        |  Put a withdraw intent                         | {"accountNumber" : "acct_123", "money" :{"balance":"1000.0","currency":"USD"}}  | "unique_token" |
|    PUT     /v1/transaction/withdraw-intent/commit |  Commit the withdraw intent                    | "unique_token"        |   HTTP Status "Ok" |


### Prerequisites

- JAVA 8
- Maven 3+

## Built With

* [Dropwizard](http://www.dropwizard.io/1.3.13/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Reladomo](https://github.com/goldmansachs/reladomo) - Reladomo is an enterprise grade object-relational mapping framework for Java.


