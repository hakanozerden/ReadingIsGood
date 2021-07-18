#!/usr/bin/env bash
cd order
mvn clean install -DskipTests
mvn spring-boot:run