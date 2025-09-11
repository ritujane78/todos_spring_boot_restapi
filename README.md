#todos

This is a basic todos project.
A signed in user can create, read, and update TODOs.
A user with ADMIN role can promote other employees to admins who can also Delete other non-admin users. 

User and Todos have one to many relation.
The table authorities is embedded to User.
Authoroties can be EMPLOYEE and/or ADMIN.

MySQL database is used.

Through Docker, MYSQL instance is run in port 3307.

The password is encrypted using 'bcrypt' algorithm and thn saved in the database.
    


Accomplishments

Create REST APIs  / Web Services with @RestController using Spring.
Implement JSON and HTTP messaging.
Install REST Client tool: Swagger
Build a CRUD interface to the database with Spring REST.
Create and use JWTs for authentication and authorization.
Handle roles and admin status.


