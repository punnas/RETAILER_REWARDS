#docker image instructions
FROM openjdk:11
LABEL maintainer="rewards.com"
EXPOSE 8080
ADD target/customer-rewards-0.0.1-SNAPSHOT.jar customer-rewards.jar
ENTRYPOINT ["java", "-jar", "customer-rewards"]