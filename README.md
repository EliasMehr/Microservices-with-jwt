# Microservice graduation project
## Developers: Jessie Eurenius, Daniel Hughes, Elias Mehr

To download this project and make it runnable, you'll need to clone the repository from the root folder

Create a new **folder** and **cd folderName**
then run the following command:
```
git clone https://github.com/EliasMehr/Microservices-with-jwt.git
```

# Docker
To build an new image run following:
```
docker-compose build
```

To run docker compose, run following:
```
docker-compose-up
```

# Eureka dashboard
To see that everything is running and all services has connected to the **eureka-server**
Paste this URL into your web browser
```
http://localhost:8761/eureka
```

# Zuul API Gateway
Zuul gateway proxy will route all requests to concerned services, the gateway is JWT Authentication based and all requests requires a valid JWT token.
The only exposed API endpoints that are non-auth required are the following:
```
http://localhost:8080/login
```
```
http://localhost:8080/register

```

# Environment variables
To add environment variables open .env file which is stored in the root folder of the project.
If you're running on macOS you may need to store the variables on your computer so that docker-compose can read the variables.

Run following in your terminal to store variables:
```
export VARIABLE-NAME=VALUE
```
Run following in your terminal to verify that the variable has been stored successfully:
```
echo $VARIABLE-NAME
```
