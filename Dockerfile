FROM openjdk:11
ADD target/pessoa-0.0.1-SNAPSHOT.jar pessoa.jar
ENTRYPOINT ["java", "-jar", "pessoa.jar"]
EXPOSE 8080