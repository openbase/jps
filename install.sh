#!/bin/bash
APP_NAME='jps'
clear &&
echo "=== clean ${APP_NAME} ===" &&
mvn clean $@ &&
clear &&
echo "=== install and deploy ${APP_NAME} ===" &&
mvn install $@ &&
clear &&
echo "=== ${APP_NAME} is successfully installed==="
