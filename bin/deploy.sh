#!/bin/bash

docker build -t anxietybox .
docker login -u AWS -p $(aws ecr get-login-password --region us-east-1) 111015081113.dkr.ecr.us-east-1.amazonaws.com
docker tag anxietybox:latest 111015081113.dkr.ecr.us-east-1.amazonaws.com/anxietybox:latest
docker push 111015081113.dkr.ecr.us-east-1.amazonaws.com/anxietybox:latest
aws ecs update-service --cluster anxietybox-cluster --service anxietybox-service --force-new-deployment