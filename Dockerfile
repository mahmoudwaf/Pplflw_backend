FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar pplflw_backend_service.jar
ENTRYPOINT ["java","-jar","/pplflw_backend_service.jar"]