FROM openjdk:8u312-oracle

COPY target/task-manager-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

CMD ["java", "-cp", "app.jar", "edu.mikedev.task_manager.App"]


