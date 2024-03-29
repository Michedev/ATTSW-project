FROM ubuntu:20.04

COPY target/task-manager-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/app.jar

WORKDIR /app

RUN apt update && apt install -y openjdk-8-jdk

CMD ["java", "-cp", "app.jar", "edu.mikedev.task_manager.App"]


