FROM openjdk:8-jre-alpine3.9
VOLUME /tmp
COPY target/*.jar pplflw_backend_service.jar
ENTRYPOINT ["java","-jar","/pplflw_backend_service.jar"]