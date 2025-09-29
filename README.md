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

## 🚀 Quick Start for Developers

### Prerequisites

**Required Software:**
- **Docker Desktop** 4.0+ (with Docker Compose)
- **Git** 2.30+
- **Java 21+** (optional, for local development)
- **Maven 3.9+** (optional, for local development)

**System Requirements:**
- 8GB RAM minimum (16GB recommended)
- 10GB free disk space
- Windows 10/11, macOS 10.15+, or Ubuntu 20.04+

### 1. Clone the Repository with Submodules

```bash
# Clone the main repository with all submodules
git clone --recursive https://github.com/AlanFarias97/acme-microservices.git

# Alternative: Clone and then initialize submodules
git clone https://github.com/AlanFarias97/acme-microservices.git
cd acme-microservices
git submodule update --init --recursive
```

**Important:** The `--recursive` flag is essential as the **Event Orchestrator** is a Git submodule.

### 2. Environment Setup

Create a `.env` file in the project root:

```bash
# Copy the example environment file
cp .env.example .env

# Or create manually with:
cat > .env << EOF
JWT_SECRET=your-secret-key-here-must-be-at-least-32-characters-long
POSTGRES_PASSWORD=admin123
EOF
```

### 3. Start the Complete Stack

**Option A: Start Everything (Recommended)**
```bash
# Start all services in background
docker-compose up -d

# Watch logs (optional)
docker-compose logs -f
```

**Option B: Start Step by Step (for debugging)**
```bash
# 1. Start infrastructure first
docker-compose up -d config-server eureka-server kafka zookeeper

# 2. Wait for infrastructure to be ready (30-60 seconds)
docker-compose logs config-server

# 3. Start databases
docker-compose up -d identity-db hr-db billing-db

# 4. Start platform services
docker-compose up -d api-gateway event-orchestrator

# 5. Start business services
docker-compose up -d identity-svc hr-svc billing-svc

# 6. Start optional services
docker-compose up -d kafka-ui
```

### 4. Verify the Setup

**Check all services are running:**
```bash
# View service status
docker-compose ps

# Check if all services are healthy
docker-compose ps --filter "status=running"
```

**Test key endpoints:**
```bash
# API Gateway (main entry point)
curl http://localhost:8080/actuator/health

# Event Orchestrator (submodule service)
curl http://localhost:8084/actuator/health

# HR Service (business service)
curl http://localhost:8083/actuator/health
```

**Access Web Interfaces:**
- **API Gateway**: http://localhost:8080
- **Eureka Dashboard**: http://localhost:8761
- **Kafka UI**: http://localhost:8090
- **Event Orchestrator**: http://localhost:8084

### 5. Stop the Stack

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (clean reset)
docker-compose down -v

# Stop and remove images (full cleanup)
docker-compose down -v --rmi all
```

## 🔧 Troubleshooting

### Common Issues

**1. Port Already in Use**
```bash
# Check what's using the port
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # macOS/Linux

# Kill the process or change ports in docker-compose.yml
```

**2. Services Not Starting**
```bash
# Check logs for specific service
docker-compose logs identity-svc

# Check all logs
docker-compose logs

# Restart specific service
docker-compose restart hr-svc
```

**3. Database Connection Issues**
```bash
# Restart databases
docker-compose restart identity-db hr-db billing-db

# Check database logs
docker-compose logs identity-db
```

**4. Submodule Issues**
```bash
# Update submodules to latest
git submodule update --remote

# Reset submodules if corrupted
git submodule deinit --all
git submodule update --init --recursive
```

**5. Build Issues**
```bash
# Clean rebuild everything
docker-compose build --no-cache
docker-compose up -d
```

### Service Startup Order

Services have dependencies and start in this order:
1. **Infrastructure**: Config Server, Zookeeper
2. **Platform**: Eureka Server, Kafka
3. **Databases**: PostgreSQL instances
4. **Gateway**: API Gateway, Event Orchestrator
5. **Business**: Identity, HR, Billing services

## 🌐 Service Endpoints

| Service | Port | Health Check | Purpose |
|---------|------|--------------|---------|
| **API Gateway** | 8080 | `/actuator/health` | Main entry point |
| **Eureka Server** | 8761 | `/actuator/health` | Service registry |
| **Config Server** | 8888 | `/actuator/health` | External configuration |
| **Event Orchestrator** | 8084 | `/actuator/health` | Kafka management |
| **Identity Service** | Internal | Via Gateway `/identity/*` | Authentication |
| **HR Service** | 8083 | Via Gateway `/hr/*` | Employee management |
| **Billing Service** | 8082 | Via Gateway `/billing/*` | Invoice management |
| **Kafka UI** | 8090 | Web Interface | Event debugging |

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