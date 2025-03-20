FROM openjdk:17-jdk

ARG JAR_PATH=build/libs
RUN JAR_FILE=$(ls ${JAR_PATH}/*.jar | head -n 1) && echo "Using JAR: ${JAR_FILE}"

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "-Duser.timezone=Asia/Seoul", "/app.jar"]
