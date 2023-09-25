#!/bin/bash

./utilities/stop-test-servers.sh

# Start test DB
docker run --rm -it -d --name brightree_test_db -p 5441:5432 -e POSTGRES_DB=brightree_test -e POSTGRES_USER=brightree_user -e POSTGRES_PASSWORD=brightree_pass postgres:14.2

# Start test REST server
docker run --rm -it -d --name test_rest_server -v $PWD/utilities/node-rest-server/init.sh:/usr/local/app/init.sh -v $PWD/utilities/node-rest-server/server.js:/usr/local/app/server.js -v $PWD/utilities/node-rest-server/package.json:/usr/local/app/package.json -p 3001:3001 node:16 /usr/local/app/init.sh
