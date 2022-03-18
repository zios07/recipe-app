# Context

The application is used to manage recipes, it publishes a REST API with endpoints to perform various actions on those recipes such as selection, creation, update and deletion.

Security wise, we have two types of users: Regular user and Admin user. Regular user with the role "USER" can perform read only operation, eg selection. The Admin user having the role "ADMIN" has the authority to CRUD the recupes. This separation of abilities is implemented to showcase the RBAC using basic auth security mechanism, and may not make much sense business wise. 
The credentials of both users are:

#### Regular User: user/password
#### Admin User: admin/password

<br>

These credentials can be changed from the application.yml file.

<br>


# Environment

#### 1 - Java 11+
#### 2 - Postgresql 11

<br>

# Running the application

#### 1 - CD into the application folder (recipes-manager)
#### 2 - Update the src/main/resources/application.yml file to use the right credentials for your postgresql server
#### 3 - run the following command : `./mvnw spring-boot:run`
#### 4 - the API should be running in the port 8080.

