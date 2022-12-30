#!/bin/bash

docker build -t anxietybox .
docker run --rm -e PORT=3000 --env-file=/Users/john/tmp/env.txt -p 3000:3000 -it anxietybox
