FROM adoptopenjdk/openjdk11:alpine as builder
RUN mkdir /app
ADD build/libs/*.jar /app/friends-management.jar
ENTRYPOINT ["java","-jar","/app/friends-management.jar"]
EXPOSE 8080
