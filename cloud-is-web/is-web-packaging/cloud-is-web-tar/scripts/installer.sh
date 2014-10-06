#!/bin/bash
# CELAR Information System Web Interface (visualizationTool)
# installation script

#
if [ -z "$CATALINA_BASE" ]; then
	echo "CATALINA_BASE should be set."
	exit 0
fi

WAR=$(find . -name "*.war")

cp $WAR $CATALINA_BASE/webapps/

exit 0