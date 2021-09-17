# ExcellenceCoinBackend

How to start the ExcellenceCoinBackend in IntelliJ
---

1. ECBApplication is the main class
1. Run it to use the standard development config
1. If you want to use another config (like the postgres dev config), you need to add specific program arguments in the run config
    1. "db migrate <config-file>" to migrate the database
    1. "server <config-file>" to start the server

How to start the ExcellenceCoinBackend application from a jar
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/ExcellenceCoinBackend-1.0.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
