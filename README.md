
## Création d'un user pour les tests
curl -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/users/sign-up

## Login pour récupérer un token valide
curl -i -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/login


## Commandes docker
### mise en route
docker-compose up -d
### arret
docker-compose down
