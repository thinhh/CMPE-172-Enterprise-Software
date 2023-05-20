# Docker
all: clean

clean:
	mvn clean

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn package

docker-build: build
	docker build --platform=linux/amd64 -t spring-starbucks-worker .
	docker images
