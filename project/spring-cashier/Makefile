all: clean

clean:
	mvn clean

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn package

run-jar: build
	java -jar target/spring-cashier-v1.0.jar


# MySQL DB

mysql:
	docker run --platform=linux/amd64 -d --network spring-cashier --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0

mysql-shell:
	docker exec -it mysql bash


# Redis DB

redis-local:
	docker run --platform=linux/amd64 --name redis --network spring-cashier -td -p 6379:6379 redis

redis-official:
	docker run --platform=linux/amd64 --name redis --network spring-cashier -td -p 6379:6379 redis:4.0

redis-shell:
	docker exec -it redis bash 


# Jumpbox

jumpbox:
	docker run --platform=linux/amd64 --network spring-cashier --name jumpbox -t -d ubuntu

jumpbox-shell:
	docker exec -it jumpbox bash 


# Docker

docker-build: build
	docker build --platform=linux/amd64 -t spring-cashier .
	docker images

cashier-network:
	docker network create --driver bridge spring-cashier

docker-run: docker-build
	docker run --platform=linux/amd64 --network spring-cashier -e "MYSQL_HOST=mysql" --name spring-cashier -td -p 9090:9090 spring-csahier

docker-clean:
	docker stop spring-csahier
	docker rm spring-csahier
	docker rmi spring-cashier

docker-shell:
	docker exec -it spring-csahier bash

docker-push:
	docker login
	docker build --platform=linux/amd64 -t $(account)/spring-cashier:v1.0 .
	docker push $(account)/spring-cashier:v1.0

# Compose

network-ls:
	docker network ls

network-create:
	docker network create --driver bridge $(network)

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale cashier=2 -d

lb-up:
	docker-compose up -d lb

cashier-up:
	docker-compose up -d cashier

redis-up:
	docker-compose up -d redis

mysql-up:
	docker-compose up -d mysql

compose-down:
	docker-compose down

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost

