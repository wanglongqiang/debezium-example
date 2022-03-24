#!/usr/bin/env bash

connector="$1"

cd "$connector" || exit
docker-compose down
