FROM openjdk:8
VOLUME /tmp
EXPOSE 8021
ADD ./target/springboot-servicio-atm-0.0.1-SNAPSHOT.jar service-atm.jar
ENTRYPOINT ["java","-jar","/service-atm.jar"]
