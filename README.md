# 🎭 Event Orchestrator Service

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-3.6.0-blue.svg)](https://kafka.apache.org/)

Centralized Kafka topic management and event orchestration service for ACME Microservices Platform.

## 🎯 Purpose

The Event Orchestrator Service is responsible for:
- **Kafka Topic Management** - Auto-creation and configuration of topics
- **Event Schema Registry** - Centralized event contract management  
- **Topic Monitoring** - Health checks and metrics for Kafka infrastructure
- **Event Routing** - Advanced routing and filtering capabilities

## 🏗️ Architecture

```
┌─────────────────────────────────────────┐
│           Event Orchestrator            │
├─────────────────────────────────────────┤
│  🎯 Topic Auto-Creation                 │
│  📊 Schema Management                   │
│  🔍 Health Monitoring                   │
│  🔄 Event Routing                       │
└─────────────────┬───────────────────────┘
                  │
          ┌───────▼───────┐
          │ Kafka Cluster │
          │ + Zookeeper   │
          └───────────────┘
```

## 📋 Managed Topics

| Topic | Partitions | Retention | Purpose |
|-------|------------|-----------|---------|
| `hr.employee.created` | 3 | 7 days | New employee events |
| `hr.employee.updated` | 3 | 7 days | Employee changes |
| `hr.employee.deleted` | 3 | 30 days | Employee removals |
| `billing.invoice.created` | 3 | 1 year | New invoices |
| `billing.invoice.paid` | 3 | 1 year | Payment events |
| `identity.user.created` | 2 | 30 days | User registration |
| `acme.dead-letter` | 1 | 30 days | Failed events |

## 🚀 Quick Start

### Prerequisites
- Java 21+
- Apache Kafka 3.6+
- Maven 3.9+

### Running Locally
```bash
# Build
mvn clean package

# Run
java -jar target/event-orchestrator-*.jar

# Or with Maven
mvn spring-boot:run
```

### With Docker
```bash
# Build image
docker build -t acme-event-orchestrator .

# Run
docker run -p 8084:8084 \
  -e KAFKA_BOOTSTRAP_SERVERS=kafka:29092 \
  acme-event-orchestrator
```

## ⚙️ Configuration

### Environment Variables
```bash
KAFKA_BOOTSTRAP_SERVERS=kafka:29092
SPRING_CLOUD_CONFIG_URI=http://config-server:8888
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka
```

### Topic Configuration
Topics are auto-created with these default settings:
- **Partitions**: 3 (for parallelism)
- **Replication Factor**: 1 (development)
- **Compression**: Snappy
- **Retention**: Varies by topic type

## 🔍 Monitoring

### Health Checks
```bash
# Service health
curl http://localhost:8084/actuator/health

# Kafka connectivity
curl http://localhost:8084/actuator/kafka
```

### Metrics
The service exposes metrics for:
- Topic creation status
- Kafka connectivity
- Message throughput
- Error rates

## 🛠️ Development

### Adding New Topics
```java
@Bean
public NewTopic myNewTopic() {
    return TopicBuilder.name("my.new.topic")
            .partitions(3)
            .replicas(1)
            .config("retention.ms", "604800000")
            .build();
}
```

### Testing
```bash
# Unit tests
mvn test

# Integration tests
mvn verify
```

## 🐳 Docker

### Build
```bash
docker build -t acme-event-orchestrator .
```

### Run
```bash
docker run -p 8084:8084 acme-event-orchestrator
```

## 🔗 Integration

This service integrates with:
- **Kafka Cluster** - Topic management
- **Eureka Server** - Service discovery
- **Config Server** - External configuration
- **All Microservices** - Event coordination

## 📚 Related

- [ACME Microservices Platform](../README.md)
- [Event Contracts](../commons/event-contracts/README.md)
- [Kafka Client](../commons/kafka-client/README.md)

## 👨‍💻 Author

**Alan Farias**
- GitHub: [@AlanFarias97](https://github.com/AlanFarias97)

---

Part of the [ACME Microservices Platform](https://github.com/AlanFarias97/acme-microservices)