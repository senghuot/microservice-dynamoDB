[![Build Status](https://travis-ci.org/senghuot/microservice-dynamoDB.svg?branch=master)](https://travis-ci.org/senghuot/microservice-dynamoDB) [![codecov](https://codecov.io/gh/senghuot/microservice-dynamoDB/branch/master/graph/badge.svg)](https://codecov.io/gh/senghuot/microservice-dynamoDB) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/530cde8bbc3d4a2596adf73710edeaa3)](https://www.codacy.com/app/senghuot/microservice-dynamoDB?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=senghuot/microservice-dynamoDB&amp;utm_campaign=Badge_Grade)

# Microservices with Netflix OSS & DynamoDB

![System Architecture](https://cdn-images-1.medium.com/max/1000/1*FwAMM5U-JOh2bMdchTKo3Q.jpeg)

## Overview
Recently, I’ve gained much interest with designing and implementing Microservices. Unlike other technologies, there aren’t many online tutorials on this subject. This documentation serves as my personal journal while I’m ramping up on the technologies and sharing my experience. You can find the document here: https://medium.com/@scottielim/microservices-w-netflix-oss-dynamodb-c3579769dc14#.rcauh8cul

## Setup
You need to setup Amazon DynamoDB if you don’t have one yet. Don’t worry AWS ‘s Free Tier allows up to 200 millions requests per month. Now, lets create a User table on DynamoDB with partition key id with type of String.

![Create Table](https://cdn-images-1.medium.com/max/1000/1*mB-kZTbGQqZctZBZg8uVXw.jpeg)

In order to access your DynamoDB, you need to setup a credential thru Amazon IAM. Amazon has a great article guiding you thru the process. Now that you have everything setup, let’s replace the DynamoDB endpoint, access key, and secret key with the real values in users-server.yml

![Properties](https://cdn-images-1.medium.com/max/800/1*QVnpjFeAbqDQWq3WwHg4Sg.jpeg)

## Run the Demo
Awesome, you have the ingredients to build the package. Thru command line, navigate to the project folder then type:
```
mvn package \\ to validate and compile the project
```
Lets run the Eureka aka Registration Server. This lets User Service to register itself in order to be discovered by the API Gateway. *Note, you can run multiple instances of User Services, just provide different port each time. Default port is 5555. In our case, we will run 2 instances.
```
java -jar target/microservice-dynamoDB-0.0.1-SNAPSHOT.jar reg
java -jar target/microservice-dynamoDB-0.0.1-SNAPSHOT.jar users 5555
java -jar target/microservice-dynamoDB-0.0.1-SNAPSHOT.jar users 5556
```
Finally, lets get the API Gateway up. It will server as a singleton for any incoming request to hit one central spot so we can abstract and scale the implementation. Simply just run:
```
java -jar target/microservice-dynamoDB-0.0.1-SNAPSHOT.jar web
```
Congratulation, you’ve setup the system - now lets check it out: http://localhost:9999/users
