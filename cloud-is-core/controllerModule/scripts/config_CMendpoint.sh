#!/bin/bash
# Configuration Script

#
FILE="config/celar/endpoint.celarmanager.properties"
WAPP_PATH=""

#
usage() {
	read -d '' USAGE <<'_EOF_'
		usage: -option1=value1 ... -optionX=valueX\\n
		options:\\n
   		   -u=*    The uri the CELAR Manager listens for requests. The format must be http://{ip}:{post}/{REST_API_root}\\n
   		   -p=*               Specifies the path the IS is installed\\n
_EOF_
	echo -e $USAGE >&2; exit 1; 
}


while getopts ":u:p:" opt; do
  case $opt in
    u)
      echo "-u was triggered, Parameter: $OPTARG" >&2
      CELAR_MANAGER=$OPTARG
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
	$WAPP_PATH=$CATALINA_BASE"\webapps\cloud-is-core"
fi

CNF_PATH=$WAPP_PATH"/"$FILE
echo $CNF_PATH

# Ensure that CELAR_MANAGER is not empty
# Before set it
if [ ! -z "$CELAR_MANAGER" ]; then
	sed -i "s|uri=[^ ]*|uri=$CELAR_MANAGER|g" $CNF_PATH
fi