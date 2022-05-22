# game-management

### Set up
In order to build and set up the project you will simply need to have docker and maven. Maven will be needed to build the project and docker in order to have 
postgresql and the application up and running.
After checkout, navigate from a cmd inside the project folder and run :
#### >mvn clean package
#### >docker-compose up

That was all! Inside the project a swagger API documentation exists in order to guide through the endpoints.
Moreover integration and unit tests are implemented to verify functionality.
