### 1. Поднять только инфраструктуру (PostgreSQL + RabbitMQ)

```bash
docker compose -f docker-compose.yaml up -d postgres rabbitmq
```

## 2. Сборка и запуск Java-сервисов вручную

```bash
mvn clean package -DskipTests

cd modules/payments
mvn spring-boot:run

cd modules/orders
mvn spring-boot:run
```

### 3. Сборка и запуск Java-сервисов с помощью Docker

### Build
```bash
cd modules/payments
mvn spring-boot:build-image -DskipTests

cd ../orders
mvn spring-boot:build-image -DskipTests
```

#### Run
```bash
docker compose up --build -d 
```