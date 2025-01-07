FROM openjdk:21
EXPOSE 8086
RUN mkdir -p /app/

ADD build/libs/*.jar /app/api-ecommerce-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/app/api-ecommerce-0.0.1.jar"]