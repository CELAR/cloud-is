#cloud-is


##CELAR Information System - Core

###Prerequisites
To successfully install and use the CELAR Information System - Core, Java (v1.7) and Apache Tomcat (v7) 
should be installed on your system 

For the  CELAR Information System - Core to operate correctly the CELAR Information System - core must be
installed also and be accessible from the Visualization Tool.

###Installation

For installing using the rpm distribution, the repository must be defined first 
under the /etc/yum.repos.d/ (e.g. CELAR.repo) with the following content:

	[CELAR-snapshots]
	name=CELAR-snapshots
	baseurl=http://snf-175960.vm.okeanos.grnet.gr/nexus/content/repositories/snapshots
	enabled=1
	protect=0
	gpgcheck=0
	metadata_expire=30s
	autorefresh=1
	type=rpm-md

To install or update the CELAR Information System - Core you have to issue
the following command

	yum update && yum install cloud-is-core

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