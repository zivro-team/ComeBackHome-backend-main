FROM openjdk:17-jdk

ARG JAR_PATH=build/libs
RUN JAR_FILE=$(ls ${JAR_PATH}/*SNAPSHOT.jar | head -n 1) && echo "Using JAR: ${JAR_FILE}"

COPY ${JAR_PATH}/*SNAPSHOT.jar app.jar  # JAR 파일을 컨테이너 내부로 복사

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "-Duser.timezone=Asia/Seoul", "/app.jar"]
