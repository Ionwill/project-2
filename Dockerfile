FROM java:8 as runner
COPY project2/target/ninjas.jar ninjas.jar
ENTRYPOINT ["java", "-jar", "/ninjas.jar"]