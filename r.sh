#!/bin/bash -ex
kill -9 $(pidof java)
nohup java -jar coronavirus-tracker-0.0.1-SNAPSHOT.jar > /dev/null &
