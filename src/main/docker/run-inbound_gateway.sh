#!/usr/bin/env bash
exec java $JAVA_OPTS -Dlogging.config=/app/logback.xml -jar /app/inbound_gateway.jar "$@"