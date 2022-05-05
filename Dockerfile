FROM fabric8/java-alpine-openjdk8-jre:1.8.0

RUN apk add --no-cache nss
WORKDIR /app
COPY target/health-*.jar healthChecker.jar

ENV SPRING_PROFILES_ACTIVE    staging

CMD ["/bin/sh", "-c", "java -jar healthChecker.jar --spring.profiles.active=$SPRING_PROFILES_ACTIVE --server.port=8080"]
