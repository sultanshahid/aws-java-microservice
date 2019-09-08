# aws-java-microservice
A micro service that invokes AWS elastic search and make it available using API gateway.

Service Architecture

https://www.lucidchart.com/invitations/accept/8e514819-bf26-4e35-9844-e4822fba4a7d


Implementation

Based on the requirements, the task inherently involved integrating AWS services like Elastic search, Lambda and API Gateway and being able to untimately query data through a endpoint.
We'll cover setting up every aws service individually and its integration.

Elastic Search Service
Test data was provided in csv file, index the test data provided by using the _bulk api to upload the documents.
