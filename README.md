# EcoHome - Smart Home Energy Tracker

A comprehensive microservices-based platform for tracking and optimizing home energy consumption. EcoHome provides real-time monitoring, analytics, and intelligent insights to help users reduce their energy usage and environmental impact.

## 📋 Project Overview

EcoHome is built using Spring Boot microservices architecture with a focus on scalability, reliability, and real-time data processing. The system tracks energy usage across multiple devices, provides analytics, generates alerts, and offers actionable insights for energy optimization.

### Key Features

- **Real-time Energy Monitoring**: Track energy consumption across connected devices
- **Device Management**: Register and manage IoT devices and smart meters
- **Usage Analytics**: Analyze consumption patterns and trends
- **Intelligent Alerts**: Get notified of unusual energy consumption patterns
- **Energy Insights**: Receive actionable recommendations to reduce energy usage
- **User Management**: Secure user authentication and profile management
- **API Gateway**: Centralized entry point for all microservices

## 🏗️ Architecture

### Microservices

The project is organized into the following microservices:

1. **User Service** - User authentication, profiles, and account management
2. **Device Service** - IoT device registration and management
3. **Usage Service** - Energy consumption data collection and storage
4. **Ingestion Service** - Data ingestion pipeline from devices
5. **Insight Service** - Analytics and energy optimization recommendations
6. **Alert Service** - Real-time alerting system for anomalies
7. **API Gateway** - Central routing and request aggregation

### Technology Stack

- **Framework**: Spring Boot 4.1.1
- **Language**: Java 21
- **Database**: MySQL 8.3.0
- **Message Queue**: Apache Kafka (KRaft mode)
- **Time-Series DB**: InfluxDB 2.7
- **Email Service**: Mailpit (local development)
- **Container Orchestration**: Docker & Docker Compose
- **Build Tool**: Maven
- **Monitoring**: Kafka UI, InfluxDB UI

## 🚀 Quick Start

### Prerequisites

- Docker & Docker Compose installed
- Java 21 JDK
- Maven 3.8+
- Git

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd EcoHome
   ```

2. **Start Docker services**
   ```bash
   docker-compose up -d
   ```

   This will start:
   - MySQL database
   - Kafka message broker
   - Kafka UI (http://localhost:8070)
   - InfluxDB time-series database
   - Mailpit email service

3. **Build and run services** (from each service directory)
   ```bash
   cd user-service
   ./mvnw spring-boot:run
   ```

   Repeat for other services:
   - device-service
   - usage-service
   - ingestion-service
   - insight-service
   - alert-service
   - api-gateway

### Environment Configuration

Each microservice has its own `application.properties` or `application.yml` configuration file. Key environment variables:

- **MySQL**: 
  - Host: `localhost:3306`
  - Username: `root`
  - Password: `password`
  - Database: `eco_home_energy_tracker`

- **Kafka**:
  - Bootstrap Servers: `kafka:9092` (internal Docker)
  - External: `localhost:9094` (host machine)
  - Cluster ID: `energy-tracker-cluster-1`

- **InfluxDB**:
  - URL: `http://localhost:8072`
  - Username: `admin`
  - Password: `admin123`
  - Organization: `leetjourney`
  - Bucket: `usage-bucket`
  - Token: `my-token`

- **Email (Mailpit)**:
  - SMTP: `localhost:1025`
  - Web UI: `http://localhost:8025`

## 📁 Project Structure

```
EcoHome/
├── user-service/              # User management microservice
├── device-service/            # Device management microservice
├── usage-service/             # Energy usage data service
├── ingestion-service/         # Data ingestion pipeline
├── insight-service/           # Analytics and insights
├── alert-service/             # Alerting system
├── api-gateway/               # API Gateway
├── docker/                    # Docker configuration files
│   └── mysql/
│       └── init.sql          # Database initialization script
├── docker-compose.yml         # Docker Compose configuration
├── EcoHome.code-workspace    # VS Code workspace file
└── README.md                  # This file
```

## 🔌 Service Ports

| Service | Port | Purpose |
|---------|------|---------|
| MySQL | 3306 | Database |
| Kafka Internal | 9092 | Message Queue (Docker) |
| Kafka External | 9094 | Message Queue (Host) |
| Kafka Controller | 9093 | Raft Quorum |
| Kafka UI | 8070 | Web Dashboard |
| InfluxDB | 8072 | Time-Series Database |
| Mailpit SMTP | 1025 | Email Service |
| Mailpit Web | 8025 | Email Dashboard |

## 📊 Data Flow

1. **Device Data Collection**: IoT devices send energy consumption data
2. **Ingestion**: Ingestion Service receives and validates data
3. **Storage**: Data stored in MySQL (relational) and InfluxDB (time-series)
4. **Event Streaming**: Events published to Kafka for real-time processing
5. **Analytics**: Insight Service processes data to generate recommendations
6. **Alerting**: Alert Service monitors for anomalies and triggers notifications
7. **API**: All functionality exposed through API Gateway

## 🛠️ Development

### Building a Service

```bash
cd <service-name>
./mvnw clean install
```

### Running Tests

```bash
cd <service-name>
./mvnw test
```

### Database Migrations

Services use Flyway for database migrations. Migration scripts are located in:
```
<service-name>/src/main/resources/db/migration/
```

## 🔐 Security

- Default credentials should be changed in production
- Enable SSL/TLS for all inter-service communication
- Implement API authentication (JWT, OAuth2)
- Use secrets management (Vault, AWS Secrets Manager)
- Restrict database access to trusted services only

## 📈 Monitoring & Debugging

### Kafka UI
Access Kafka topics, consumer groups, and broker information:
```
http://localhost:8070
```

### InfluxDB UI
View and query time-series data:
```
http://localhost:8072
```

### Mailpit Email Dashboard
Monitor emails sent during development:
```
http://localhost:8025
```

### Service Logs
```bash
docker-compose logs -f <service-name>
```

## 🚨 Troubleshooting

### Kafka Connection Issues
- Ensure Kafka container is running: `docker-compose ps`
- Check Kafka logs: `docker-compose logs kafka`
- Verify bootstrap servers configuration in services

### MySQL Connection Issues
- Verify MySQL container is running and healthy
- Check database credentials in application.properties
- Ensure init.sql has been executed

### InfluxDB Issues
- Verify token and organization settings
- Check bucket exists: `usage-bucket`
- Ensure retention policy is set correctly

## 📝 API Documentation

API documentation for each service:

- **User Service**: `/swagger-ui.html` (if Springdoc added)
- **Device Service**: `/swagger-ui.html`
- **Usage Service**: `/swagger-ui.html`
- **API Gateway**: Acts as reverse proxy for all services

## 🤝 Contributing

1. Create a feature branch
2. Make your changes
3. Write/update tests
4. Commit with clear messages
5. Push and create a pull request

## 📄 License

[Add your license information here]

## 👨‍💻 Developers

- Suraj Gupta

## 📞 Support & Contact

For issues, questions, or suggestions, please open an issue or contact the development team.

## 🔗 Related Projects

- [Home Energy Tracker](../home-energy-tracker/) - Alternative implementation

---

**Last Updated**: November 2026

**Status**: Active Development
