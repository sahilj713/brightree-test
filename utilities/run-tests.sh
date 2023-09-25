#!/bin/bash

./utilities/start-test-servers.sh
./mvnw verify
./utilities/stop-test-servers.sh
