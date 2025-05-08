FROM openjdk:17-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 실행 가능 JAR 파일을 복사
COPY build/libs/Comebackhome-be-0.0.1-SNAPSHOT.jar app.jar

# Spring Boot 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "/app/app.jar"]
