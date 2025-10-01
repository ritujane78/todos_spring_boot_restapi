# Todos Project

## Project Overview
This is a basic **Todos** project.  
A signed-in user can **create**, **read**, and **update** TODOs.

A user with the **ADMIN** role can:
- Promote other employees to admin.
- Delete other non-admin users.

### Data Model
- **User ↔ Todos:** One-to-many relationship.
- **Authorities:** Embedded within User.  
  Authorities can be `EMPLOYEE` and/or `ADMIN`.

### Database
- **MySQL** is used.
- MySQL instance runs through **Docker** on port `3307`.

### Security
- Passwords are encrypted using the **bcrypt** algorithm before being saved to the database.

---

## Accomplishments
- Created REST APIs / Web Services using `@RestController` in Spring.
- Implemented **JSON** and **HTTP messaging**.
- Installed and configured REST client tool: **Swagger**.
- Built CRUD interface to the database with Spring REST.
- Created and used **JWTs** for authentication and authorization.
- Handled roles and admin status.

---
