.PHONY: up down clean test test-docker

up:
	docker-compose up --build -d

down:
	docker-compose down

clean:
	docker-compose down -v --rmi all
	docker system prune -f

test:
	mvn test

test-docker:
	docker run --rm -v "$(shell pwd):/app" -w /app maven:3.9-eclipse-temurin-17 mvn test
