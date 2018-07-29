#!/usr/bin/env bash
mvn clean package -Dmaven.test.skip=true -U
docker build -t 192.168.76.61:8080/library/sell-eureka .
docker push 192.168.76.61:8080/library/sell-eureka
