# S3_FriendsAPI_DoLai
This is an application with a need to build its own social network, "Friends Management" is a common requirement which usually starts off simple but can grow in complexity depending on the application's use case. Usually, the application compraised of features like "Friend", "Unfriend", "Block", "Receive Updates" etc.
## Technical Stack
- Spring boot with Gradle 
- Mysql
- Mybatis
## Library and IDE and Build Tools
- JDK (Java Development Kit) 11
- Docker
- Docker Compose
- IntelliJ IDEA (or Eclipse)
## Run App

Start Docker

Run Docker compose to run Mysql in local on port 3306
```shell script
$ cd /path/to/project
$ docker-compose up -d
```
Run Application
```shell script
gradlew bootJun
```
Can test api at http://localhost:8080/friends-management

##List of REST Endpoints and Explanation
Returns a list of friends of a person.
Path : /friends

Input :
```
{
 	"email":"abc@example.com"
}
```
Sample Output :
```
{
 	"success": true,
 	"friends":[
 		"xyz@gmail.com",
 		"xyz2@gmail.com",			
 	],
 	"count":2
 }
```
Defined Errors :   

404 : Not found email email request

Returns list of common friends of two persons
Path : /common

Input :
```
{
	"friends":[
		"abc@gmail.com",
		"xyz@gmail.com"
	]
}
```
Output :
```
{
	"success": true,
	"friends":[
		"xyz2@gmail.com"
	],
	"count":1
}
```

Defined Errors : 

400 : Friend list cannot be empty

400 : Please input two emails valid

404 : Email does not exists

Establish Friendship between two persons
Path : /connect

Input :
```
{
	"friends":[
		"abc@gmail.com",
		"pqr@gmail.com"
	]
}
```
Output :
```
{
	"success": true
}
```
Defined Errors : 

400 : Friend list cannot be empty

400 : Please input two emails valid

404 : Email does not exists

409 : friend already

406 : Friend has been blocked

Person subscribe to another Person
Path : /subscribe

Input :
```
{
	"requestor":"abc@gmail.com",
	"target":"pqr@gmail.com"
}
```
Output :
```
{
	"success": true
}
```
Defined Errors : 

400 : Please input two emails valid

406 : Person target Blocked person request 

404 : Email does not exists

409 : subscribe already

Person block updates from another Person
Path : /block

Input :
```
{
	"requestor":"example@example.com",
	"target":"example2@example.com"
}
```
Output :
```
{
	"success": true
}
```
Defined Errors : 

400 : Please input two emails valid 

404 : Email does not exists 

402 : Friend has been blocked 

Post an update which returns a list of emails that will receive the update.
Path : /receive

Input :
```
{
	"sender":"abc@gmail.com",
	"text":"Hello, how are you ! xyz@gmail.com"
}
```
Output :
```
{
	"success": true,
	"recipients":[
		"xyz@gmail.com",
		"pqr@gmail.com"			
	]
}
```
Defined Errors : 

400 : sender must not be blank

404 : Not found email Sender

Other Errors

The error response has the below format
```
{
    "timestamp": "2020-04-02T09:47:14.692+0000",
    "status": error Code,
    "error": "Error description",
    "message": "Error message",
    "path": "/friends-management/friends"
}
```
## Deployment using Docker
Step 1. Build App
```shell script
gradlew build
```
Step 2. Copy .build folder , Dockerfile, docker-compose.yaml , mysql-db folder to host

ON Host
- Create network
```shell script
docker network create friends-management-app
```
- Build image
```shell script
$ docker build --build-arg JAR_FILE=build/libs/*.jar -t friends-management-img .
```
- Up docker compose
```shell script
$ docker-compose up -d
```
App Starting on http://iphost:8080
