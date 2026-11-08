FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

# bagimliliklar ayri layer'da cache'lensin
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src

ENTRYPOINT ["mvn", "-B", "test"]
