#!/bin/bash
set -e
export PATH=$PATH:/usr/local/maven/bin
export POD_ID=dev
# export version="dev"
cd sample-09032022; mvn clean package test; cd -


cd test; mvn clean package test; cd -


cd sample-09032022; dockerbuild  -t ${DOCKER_REGISTRY}/sample-service-ying/sample-09032022:${POD_ID} . ; cd -


cd test; dockerbuild  -t ${DOCKER_REGISTRY}/sample-service-ying/test:${POD_ID} . ; cd -



cd nginx; dockerbuild  -t ${DOCKER_REGISTRY}/sample-service-ying/nginx:${POD_ID} . ; cd -



cd nfs; dockerbuild  -t ${DOCKER_REGISTRY}/sample-service-ying/nfs:${POD_ID} . ; cd -

