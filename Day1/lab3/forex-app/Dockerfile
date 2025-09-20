FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/forex-app-1.0-SNAPSHOT.jar app.jar
COPY rates.csv rates.csv
ENTRYPOINT ["java", "-cp", "app.jar", "com.example.forex.ForexConverter", "rates.csv"]
