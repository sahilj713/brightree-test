#!/bin/bash

# Stop the containers (docker will automatically remove these)
docker stop test_rest_server || true
docker stop brightree_test_db || true
