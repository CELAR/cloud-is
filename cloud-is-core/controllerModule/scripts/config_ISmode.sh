#!/bin/bash
# Configuration Script

#
FILE="config/config.properties"
WAPP_PATH=""

#
usage() {
	read -d '' USAGE <<'_EOF_'
		usage: -option1=value1 ... -optionX=valueX\\n
		options:\\n
   		   -m={celar|test}    Specifies the mode that IS will run\\n
   		   -p=*               Specifies the path the IS is installed\\n
_EOF_
	echo -e $USAGE >&2; exit 1; 
}


while getopts ":m:p:" opt; do
  case $opt in
    m)
      echo "-m was triggered, Parameter: $OPTARG" >&2
      MODE=$OPTARG
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
	$WAPP_PATH=$CATALINA_BASE"\webapps\cloud-is"
fi

CNF_PATH=$WAPP_PATH"/"$FILE
echo $CNF_PATH

# Ensure that MODE is not empty
# Before set it
if [ ! -z "$MODE" ]; then
	sed -i "s|mode=[^ ]*|mode=$MODE|g" $CNF_PATH
fi