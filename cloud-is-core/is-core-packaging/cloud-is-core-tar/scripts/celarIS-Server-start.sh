#!/bin/bash

JAR="${is-core.jar-packaging.name}.jar"
CELAR_IS_SERVER_HOME="/usr/local/bin/celarISServerDir"
CELAR_IS_LOCK="/var/lock/celarIS-Service-lock"
java -jar $CELAR_IS_SERVER_HOME/$JAR  $CELAR_IS_SERVER_HOME $CELAR_IS_LOCK & 