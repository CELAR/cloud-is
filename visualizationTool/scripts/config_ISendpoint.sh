#!/bin/bash
# Configuration Script

# 
FILE="config/init.properties"
WAPP_PATH=""

#
usage() {
	read -d '' USAGE <<'_EOF_'
		usage: -option1=value1 ... -optionX=valueX\\n
		options:\\n
   		   -u=*    The uri the IS Server listens for requests. The format must be http://{ip}:{post}/{REST_API_root}\\n
   		   -p=*               Specifies the path the IS is installed\\n
_EOF_
	echo -e $USAGE >&2; exit 1; 
}

while getopts ":u:p:" opt; do
  case $opt in
    u)
      echo "-u was triggered, Parameter: $OPTARG" >&2
      IS_SERVER=$OPTARG
      ;;
    p)
      echo "-p was triggered, Parameter: $OPTARG" >&2
      WAPP_PATH=$OPTARG
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
    :)
      echo "Option -$OPTARG requires an argument." >&2
      exit 1
      ;;
  esac
done

# If no parameters where given
# Show help message
if [[ $@ ]]; then
    true
else
    usage
    exit 1
fi


# Check if path was set 
# if not set the default
if [ -z "$WAPP_PATH" ]; then
	$WAPP_PATH=$CATALINA_BASE"\webapps\visualizationTool"
fi

CNF_PATH=$WAPP_PATH"/"$FILE
echo $CNF_PATH

# Ensure that IS_SERVER is not empty
# Before set it
if [ ! -z "$IS_SERVER" ]; then
	sed -i "s|isserver.ip=[^ ]*|isserver.ip=$IS_SERVER|g" $CNF_PATH
fi