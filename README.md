# aws-java-microservice
A micro service that invokes AWS elastic search and make it available using API gateway.

# Service Architecture

![AWS Serverless Image Moderation Chatbot](https://user-images.githubusercontent.com/28723926/64492320-9eb91d00-d227-11e9-8ab4-6150f68699d3.jpeg)

# Implementation

Based on the requirements, the task inherently involved integrating AWS services like Elastic search, Lambda and API Gateway and being able to untimately query data through a endpoint.
We'll cover setting up every aws service individually and its integration.

# Elastic Search Service

Login to AWS account and create a Amazon Elastic search domain, define a domain name for elastic search domain and choose an instance type, its recommended to use t2.small.elasticsearch, a small and inexpensive instance type suitable for testing purposes.
For Setting the domain access policy to, choose Allow access to the domain from specific IP(s) and enter your public IP address, which you can find by searching for "What is my IP?" on most search engines.

Test data provided in csv file needs to be converted to JSON format so that it could be added to elastic search index. You could convert the csv either programatically or using a tool. You could use Sqlify to convert large csv to json file.
Upload test data to Amazon Elasticsearch Service domain for indexing using the bulk APIs from the command line.
Use the bulk API to add or update multiple Elasticsearch documents that are described in the same JSON file.

Elasticsearch uses a REST API, numerous methods exist for indexing documents. You can use standard clients like curl or any programming language that can send HTTP requests.

# To upload a JSON file that contains multiple documents to an Amazon ES domain

curl -XPOST http://search-XXX-XXXXXXXXXXXXXXXXXXX.us-XXX.es.amazonaws.com/personal-capital/_bulk?pretty --data-binary @42.json -H 'Content-Type: application/json'

Note: While uploading the json file at one request you cannot upload more than 10MB data. So split your massive json file into multiple smaller json files. Every Json file needs to be terminated by a new line [\n].

We could verify the data upload completed sucessfully at Kibana dashboard.

# Lambda

Sign in to AWS console and create a Lambda, choose Java 8 to create Lambda function.
Create a spring boot starter project using spring.initializer, to create a java handler. 
Which internally makes call to elastic search endpoint based on parameters in the request e.g. sponsorName, sponsorState
and planName.

As part of the maven dependencies in pom.xml, include the artifactId (that is, aws-lambda-java-core) is the AWS Lambda core library that provides definitions of the RequestHandler, RequestStreamHandler, and the Context AWS Lambda interfaces for use in your Java application. At the build time Maven resolves these dependencies.

In the plugins section, we need the Apache maven-shade-plugin that Maven will download and use during your build process. This plugin is used for packaging jars to create a standalone .jar (a .zip file), your deployment package. 
After you create the deployment package, you can use the console to upload the package to AWS Lambda to create your Lambda function. You can also use the console to test the function by manually invoking it.

When you package and upload this code to create your Lambda function, you specify the "packagenameOfLambdaFunction.ClassnameofLamdaFunction::handlerName" method reference as the handler.

You upload the deployment package(.jar or .zip) to S3 bucket and provide the path of S3 bucket to Lambda Function.


# API Gateway

Before creating an GET api which invokes our Lambda function. In order for your API to invoke your Lambda function, you'll need to have an API Gateway assumable IAM role, which is an IAM role with the trusted relationship. 
The role you create will need to have Lambda InvokeFunction permission. Otherwise, the API caller will receive a 500 Internal Server Error response.
Create the IAM role and enter the role ARN for the lambda_invoke_function_assume_apigw_role role you created for the lambda function.

Now proceed with creating the API, In the API Gateway console, choose Create API and set the Endpoint Type set to Regional.
Create a GET method that passes query string parameters(sponsorName, sponsorState and planName) to the Lambda function, you enable the API to be invoked from a browser.

As part of setting up the GET method, in Integration Request set up the mapping template to translate the client-supplied query strings to the integration request payload as required by the Lambda function.

The three query string parameters(sponsorName, sponsorState and planName) declared in Method Request into designated property values of the JSON object as the input to the backend Lambda function. The transformed JSON object will be included as the integration request payload.

# End-to-End Test

To retrieve data based on Plan Name, pass the value for planName query parameter: planName=GF & SP RETIREMENT PLAN TRUST

https://nzrteq0ayl.execute-api.us-east-1.amazonaws.com/dev/personalCapitalSearch?planName=GF%20&%20SP%20RETIREMENT%20PLAN%20TRUST

To retrieve data based on Sponsor Name, pass the value for sponsorName query parameter: sponsorName=SMOKY MOUNTAIN FOOD SERVICES, INC

https://nzrteq0ayl.execute-api.us-east-1.amazonaws.com/dev/personalCapitalSearch?sponsorName=SMOKY%20MOUNTAIN%20FOOD%20SERVICES,%20INC

To retrieve data based on Sponsor State, pass the value for sponsorState query parameter: sponsorState=TX

https://nzrteq0ayl.execute-api.us-east-1.amazonaws.com/dev/personalCapitalSearch?sponsorState=TX
