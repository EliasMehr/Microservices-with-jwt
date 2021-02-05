# Microservices graduation project
## Developers: Jessie Eurenius, Daniel Hughes, Elias Mehr

To download this project and make it runnable, you'll need to clone the repository from the root folder

Create a new **folder** and **cd folderName**
then run the following command:
```
git clone https://github.com/EliasMehr/Microservices-with-jwt.git
```

# Getting Started
You can run the project more easily using docker compose as described in the next section.

The project may also be run locally by creating an empty database for each application, entering environment variables and running RabbitMQ from a docker image with the following command:
```
docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management
```

## Docker Compose
To build the project run the following:
```
docker-compose build
```

To run docker compose, run the following:
```
docker-compose-up
```

## Eureka dashboard
To see that everything is running and all services has connected to the **eureka-server**
Paste this URL into your web browser
```
http://localhost:8761/eureka
```

## RabbitMQ dashboard
You can inspect the RabbitMQ message broker by going to this address and logging in as 'guest' with the password 'guest':
```
http://localhost:15672/
```

## Zuul API Gateway
Zuul gateway proxy will route all requests to concerned services, the gateway is JWT Authentication based and all requests requires a valid JWT token.
The only exposed API endpoints that are non-auth required are the following:
```
http://localhost:8080/login
```
```
http://localhost:8080/user/register/company
http://localhost:8080/user/register/customer
```
```
http://localhost:8080/campaign/all/published
```

## Environment variables
To add environment variables open .env file which is stored in the root folder of the project.
If you're running on macOS you may need to store the variables on your computer so that docker-compose can read the variables.

On macOS, run the following in your terminal to store variables:
```
export VARIABLE-NAME=VALUE
```
Then you can run the following in your terminal to verify that the variable has been stored successfully:
```
echo $VARIABLE-NAME
```

# Project Structure
![Project Structure](https://i.imgur.com/RCBCLYf.png)

# Project Overview
![Project Overview](https://i.imgur.com/v6eIXzu.png)

## Eureka Server
Eureka allows services to register so that Zuul Gateway may handle routing correctly.

## Zuul Gateway
When running the project through docker compose, all applications are inside a closed docker network. Zuul Gateway application at port 8080 is responsible for routing and security. After a user is registered, they must confirm their email and then login. After a successful login, Zuul returns a jwt token along with the user role. The jwt token must be supplied as a Bearer token to any request that requires authentication. Zuul Gateway application supplies the currently logged in user id to the services.

## User Service
Users may register as a company user or customer user and some details may differ, but most importantly the access rights are different. A company user may create and modify campaigns while a customer user may only view campaigns. User Service allows users to create, update and remove their account. User service informs other services about what happens to the user database so they can update relevant information in their own databases.

## Campaign Service
Company users may create campaigns which customers will be able to view and get a discount code for. Published campaigns are open to view for the public, but login is required to access the discount code. Company users may create, update and delete campaigns, which may be set as immediately published or to be automatically published at a later date.

## Confirmation Token Service
When a user registers, a confirmation token is created. This token is sent to email service. When a user clicks the link in their confirmation email, Confirmation Token Service confirms the token and informs other services that the user should be enabled and permissions should be created.

## Email Service
Email Service awaits name and email from User Service as well as a token from Confirmation Token Service. Once all information has been received for a user id, a confirmation link email is sent to the newly registered user.

## Permissions Service
When a user has confirmed their email, Permissions Service is notified and will create a permissions object for that user id. Zuul Gateway is informed so it may track permissions.

## Additional Information
For more information, see the java docs for each service.
