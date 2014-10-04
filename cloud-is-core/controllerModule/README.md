#cloud-is


##CELAR Information System - Core

###Configuration
To configure CELAR Information System Core (controllerModule) a user must alter the files in

		{extracted_webapp_folder}/config

The property `mode` is set to `celar` (which is the default value) if the Information System is installed under the CELAR umbrella
or it should be set to `test` if someone wants to run Information System in a standalone mode for testing purposes. 
While the `mode` is set to `dummy` the dataCollection module generates random data to showcase the Information System functionality.

In a second step the properties at the path

		{extracted_webapp_folder}/config/celar/endpoint.celarmanager.properties
	
need to be set to the correct CELAR Manager url parameters

To ease the configuration process a user can user the scripts under the scripts folder

**config_ISmode.sh**

	Usage:
	-option1=value1 ... -optionX=valueX
		options:
			-m={celar|test}    Specifies the mode that IS will run
			-p=*               Specifies the path the IS is installed


**config_CMendpoint.sh**

	Usage:
	-option1=value1 ... -optionX=valueX
		options:\\n
			-u=*		The uri the CELAR Manager listens for requests. The format must be http://{ip}:{post}/{REST_API_root}
			-p=*		Specifies the path the IS is installed