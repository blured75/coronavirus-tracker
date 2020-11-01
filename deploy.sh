#!/bin/bash
mvn package
scp -i ~/aws_blured.pem target/coronavirus-tracker-0.0.1-SNAPSHOT.jar ubuntu@ec2-3-125-115-135.eu-central-1.compute.amazonaws.com:/home/ubuntu
ssh -i ~/aws_blured.pem ubuntu@ec2-3-125-115-135.eu-central-1.compute.amazonaws.com ./r.sh
