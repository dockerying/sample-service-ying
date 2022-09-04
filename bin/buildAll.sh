#!/bin/bash
set -e

export PATH=$PATH:/usr/local/maven/bin
export DOCKER_REGISTRY=docker-dev-repo.aws.ariba.com
export BASE_IMAGE_VERSION=290-11-g64f1e9f

echo Stopping existing jobs...
stopdeployment sample-service-ying

./bin/bootstrap.sh
./bin/buildapp.sh
cd cobalt; deploy; cd -

# 
# ./bin/runTests.sh
# 
