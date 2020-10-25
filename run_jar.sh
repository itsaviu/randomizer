#!/usr/bin/env bash
./gradlew build -x test

java -Xms512m -Xmx512m -jar build/libs/randomizer-0.0.1-SNAPSHOT.jar
