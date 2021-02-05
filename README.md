# Microservice graduation project
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
