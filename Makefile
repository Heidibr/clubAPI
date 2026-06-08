MVN := ./mvnw

.PHONY: run test clean up down

build: 
	$(MVN) clean install

run: 
	$(MVN) spring-boot:run

test: 
	$(MVN) test

clean:
	$(MVN) clean

up:
	docker compose up --build --watch

down:
	docker compose down
