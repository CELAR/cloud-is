#cloud-is


##CELAR Information System - Visualization Tool

###Configuration
The CELAR Information System visualizationTool can be configured after its installation, by altering the files in

	{extracted_webapp_folder}/config

More specifically the property `issendpoint.ip` in the `init.properties` should be set to the address that the
is-core (controllerModule) is deployed.

To ease the configuration process a user can user the scripts under the scripts folder

**config_ISendpoint.sh**
	
	Usage:
	-option1=value1 ... -optionX=valueX
		options:\\n
			-u=*    		The uri the IS Server listens for requests. The format must be http://{ip}:{post}/{REST_API_root}
			-p=*			Specifies the path the IS is installed