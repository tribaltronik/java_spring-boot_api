.PHONY: up down clean

up:
	docker-compose up --build -d

down:
	docker-compose down

clean:
	docker-compose down -v --rmi all
	docker system prune -f
