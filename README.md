# Spring_Security_MVC
This is a standard MVC application template that can be used for building applications. 
The application supports two classes: User and Roles, which are connected through a @ManyToMany relationship.

1. Spring Security configuration has been set up.
2. A Role class has been created, and the User is linked to roles in a way that allows a user to have multiple roles.
3. The Role and User models have been implemented with the GrantedAuthority and UserDetails interfaces respectively.
4. All CRUD operations and their corresponding pages should only be accessible to users with an "admin" role via the URL: /admin/.
5. Users with a "user" role should only have access to their home page /user, where their data is displayed.
6. Access to this page should be granted only to users with "user" and "admin" roles.
7. Logout functionality has been configured on any page using Thymeleaf features.
8. The LoginSuccessHandler has been configured so that after authentication, an admin is redirected to the /admin page, and a user is directed to their /user page.
