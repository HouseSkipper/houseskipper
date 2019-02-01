#!/bin/bash

cd /deploy/houseskipper-back
fkill :56723
docker-compose down
docker-compose up -d
sudo chmod -R 777 db_houseskipper
java -Dserver.port=56723 -Dspring.profiles.active=prod $JAVA_OPTS -jar target/HouseSkipper-0.0.1-SNAPSHOT.jar >> logBack.txt &