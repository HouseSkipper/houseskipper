#!/bin/bash

cd /deploy/houseskipper-back
docker-compose up -d
java -Dserver.port=8001 -Dspring.profiles.active=prod $JAVA_OPTS -jar target/HouseSkipper-0.0.1-SNAPSHOT.jar