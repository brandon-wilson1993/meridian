This is a project to build out a bnaking application for me to better understand Spring Boot and any other technologies I plan to use (Kafka, SQS, and DynamoDB come to mind right away). CI/CD pipeline using Github Actions has been created. Currently the process goes as follows:
1. Build and run unit tests using JUnit and Mockito (with code coverage metrics using Jacoco)
2. Start docker compose with the required services and the application
3. Run integration tests using Rest Assured against the docker compose application

There is also a Trello board here: https://trello.com/b/klyIsfcU/meridian. This will serve as the layout for what work I have planned.


_Note:_ Once this is at a good point, my intention is to also make this same application in .NET and C++ (others to be included later) so that I have an understanding of how to build an application across multiple languages.
