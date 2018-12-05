
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

## Exemple de données pour le post lors d'un ajout d'une maison (l'utilisateur admin doit être créé avant)
{
    "address": "5 rue marechal Gallieni",
    "amperage": "30",
    "city": "Nancy",
    "comment": "",
    "constructionYear": "1996",
    "heatingType": "électrique",
    "houseName": "appart1",
    "houseType": "appartement",
    "livingSpace": "33",
    "numberPieces": "3",
    "outsideSpace": "0",
    "postalCode": "54000",
    "rooms": [
        {
            "description": "Cuisine",
            "roomName": "Cuisine",
            "space": "5"
        },
        {
            "description": "Chambre + Salon",
            "roomName": "Chambre",
            "space": "23"
        }
    ],
    "standardType": "Studio",
    "standardTypeNumber": "1",
    "username":"admin"
}