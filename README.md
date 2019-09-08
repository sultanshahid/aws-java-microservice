# aws-java-microservice
A micro service that invokes AWS elastic search and make it available using API gateway.

# Service Architecture

![AWS Serverless Image Moderation Chatbot](https://user-images.githubusercontent.com/28723926/64492320-9eb91d00-d227-11e9-8ab4-6150f68699d3.jpeg)

# Implementation

Based on the requirements, the task inherently involved integrating AWS services like Elastic search, Lambda and API Gateway and being able to untimately query data through a endpoint.
We'll cover setting up every aws service individually and its integration.

# Elastic Search Service
Test data was provided in csv file, index the test data provided by using the _bulk api to upload the documents.
