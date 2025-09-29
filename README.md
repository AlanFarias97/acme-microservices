# 🏢 ACME Microservices Platform

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-3.6.0-blue.svg)](https://kafka.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue.svg)](https://docs.docker.com/compose/)

Enterprise-grade microservices platform featuring **Event-Driven Architecture**, **Service Discovery**, **API Gateway**, and **Distributed Caching**.

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    API Gateway (8080)                      │
└─────────────────────┬───────────────────────────────────────┘
                      │
      ┌───────────────┼───────────────┐
      │               │               │
┌─────▼─────┐   ┌─────▼─────┐   ┌─────▼─────┐
│ Identity  │   │    HR     │   │  Billing  │
│ Service   │   │ Service   │   │ Service   │
└─────┬─────┘   └─────┬─────┘   └─────┬─────┘
      │               │               │
      └───────────────┼───────────────┘
                      │
              ┌───────▼───────┐
              │  Kafka Cluster │
              │  + Zookeeper   │
              └───────┬───────┘
                      │
              ┌───────▼───────┐
              │  Redis Cluster │
              │   (Cache)      │
              └───────────────┘
```

## 🚀 Key Features

### 🎯 **Event-Driven Architecture**
- **Apache Kafka** para messaging asíncrono
- **Typed Event Contracts** en commons library
- **Auto-topic creation** y management
- **Dead Letter Queues** para error handling

### 🔧 **Microservices Foundation**
- **Service Discovery** con Eureka
- **API Gateway** con load balancing y circuit breakers
- **Centralized Configuration** con Config Server
- **JWT Authentication** entre servicios

### 📊 **Observability & Monitoring**
- **Kafka UI** para debugging visual
- **Health checks** en todos los servicios
- **Actuator endpoints** para métricas
- **Structured logging**

### 🏭 **Enterprise Patterns**
- **Multi-module Maven** project
- **Git Submodules** para servicios independientes
- **Docker Compose** para desarrollo
- **Professional project structure**

## 📂 Project Structure

```
acme-microservices/
├── 📦 commons/                    # Shared Libraries
│   ├── event-contracts/           # Typed event models
│   ├── kafka-client/             # Kafka abstractions
│   ├── cache-client/             # Redis utilities
│   └── common-security/          # JWT shared logic
├── 🏛️ platform/                   # Infrastructure Services
│   ├── api-gateway/              # Spring Cloud Gateway
│   ├── config-server/            # External configuration
│   ├── eureka-server/            # Service discovery
│   └── event-orchestrator/       # Kafka management (Git Submodule)
├── 🎯 services/                   # Business Services
│   ├── identity-svc/             # Authentication & users
│   ├── hr-svc/                   # Human resources
│   └── billing-svc/              # Invoice management
├── docker-compose.yml            # Complete infrastructure
└── pom.xml                       # Parent POM
```

## 🛠️ Technology Stack

| Component | Technology | Purpose |
|-----------|------------|---------|
| **Language** | Java 21 | Latest LTS with performance improvements |
| **Framework** | Spring Boot 3.4.5 | Microservices foundation |
| **Service Discovery** | Eureka | Service registry and discovery |
| **API Gateway** | Spring Cloud Gateway | Routing, load balancing, security |
| **Messaging** | Apache Kafka 3.6.0 | Event streaming platform |
| **Cache** | Redis | Distributed caching |
| **Database** | PostgreSQL 16 | Relational data storage |
| **Security** | JWT + Spring Security | Authentication & authorization |
| **Configuration** | Spring Cloud Config | Externalized configuration |
| **Containerization** | Docker + Docker Compose | Development environment |

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

## 🔗 Git Submodules

This project demonstrates **enterprise microservices practices** using Git submodules for independent service development.

### Event Orchestrator (Submodule)
The `platform/event-orchestrator` is managed as a separate repository:
- **Repository**: [event-orchestrator](https://github.com/AlanFarias97/event-orchestrator)
- **Purpose**: Independent development, versioning, and deployment of Kafka management
- **Build**: Self-contained with multi-stage Dockerfile

### Working with Submodules

**Daily Development:**
```bash
# Update all submodules to latest
git submodule update --remote

# Update specific submodule
git submodule update --remote platform/event-orchestrator

# Check submodule status
git submodule status
```

**Working on Submodule:**
```bash
# Navigate to submodule
cd platform/event-orchestrator

# Work normally (it's a separate Git repo)
git checkout -b feature/new-feature
git commit -m "Add new feature"
git push origin feature/new-feature

# Return to main project and update reference
cd ../..
git add platform/event-orchestrator
git commit -m "Update event-orchestrator to latest version"
```

### Why Submodules?

✅ **Independent Development**: Teams can work on services separately  
✅ **Independent Versioning**: Each service has its own release cycle  
✅ **Independent CI/CD**: Separate build and deployment pipelines  
✅ **Code Ownership**: Clear boundaries between team responsibilities  
✅ **Enterprise Practice**: Standard approach in large organizations

## 🧪 Testing

### Unit Tests
```bash
# Test all modules
mvn test

# Test specific service
cd services/hr-svc
mvn test
```

### Integration Tests
```bash
# Run integration tests
mvn verify
```

### Manual Testing
```bash
# Get JWT token
curl -X POST http://localhost:8080/identity/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","password":"demo"}'

# Use token for authenticated calls
curl -H "Authorization: Bearer <TOKEN>" \
  http://localhost:8080/hr/employees
```

## 🔧 Development

### Adding New Events
1. Define event in `commons/event-contracts`
2. Add topic in `event-orchestrator/KafkaTopicConfiguration`
3. Implement producer in source service
4. Implement consumer in target service

### Adding New Service
1. Create module in `services/`
2. Add dependency on commons modules
3. Configure Eureka client
4. Add routes in API Gateway
5. Update docker-compose.yml

## 🌟 Roadmap

- [ ] **Cache Implementation** - Redis multi-level caching
- [ ] **Distributed Tracing** - Sleuth + Zipkin
- [ ] **Metrics & Monitoring** - Prometheus + Grafana  
- [ ] **Event Sourcing** - Complete audit trail
- [ ] **SAGA Pattern** - Distributed transactions
- [ ] **API Documentation** - OpenAPI/Swagger
- [ ] **CI/CD Pipeline** - GitHub Actions
- [ ] **Kubernetes Deployment** - Helm charts

## 📚 Documentation

- [Event Contracts Guide](commons/event-contracts/README.md)
- [Service Communication](docs/communication.md)
- [Security Setup](docs/security.md)
- [Kafka Configuration](docs/kafka.md)
- [Docker Deployment](docs/deployment.md)

## 👨‍💻 Author

**Alan Farias**
- GitHub: [@AlanFarias97](https://github.com/AlanFarias97)

---

Part of the [ACME Microservices Platform](https://github.com/AlanFarias97/acme-microservices)