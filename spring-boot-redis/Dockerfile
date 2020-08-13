FROM openjdk:8
ADD target/spring-boot-redis.jar spring-boot-redis.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "spring-boot-redis.jar"]