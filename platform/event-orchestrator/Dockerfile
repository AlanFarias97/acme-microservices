FROM openjdk:21-jdk-slim

WORKDIR /app

# Copiar el JAR
COPY target/event-orchestrator-*.jar app.jar

# Crear usuario no-root
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown appuser:appuser /app
USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8084/actuator/health || exit 1

# Exposer puerto
EXPOSE 8084

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
