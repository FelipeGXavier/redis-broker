FROM adoptopenjdk/openjdk11

ADD target/consumer-*.jar /app/app.jar

WORKDIR /app

RUN java -version

CMD ["java", "-jar", "app.jar"]

ENV JAVA_OPTS="-XX:PermSize=1024m -XX:MaxPermSize=1024m"

EXPOSE 8081