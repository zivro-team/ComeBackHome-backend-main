FROM openjdk:17-jdk

WORKDIR /app

# JAR 복사
COPY build/libs/Comebackhome-be-0.0.1-SNAPSHOT.jar app.jar

# wait-for-it 복사 및 권한 부여
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# entrypoint는 wait-for-it으로 변경
ENTRYPOINT ["/wait-for-it.sh"]
CMD ["elasticsearch:9200", "--", "java", "-jar", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "/app/app.jar"]
