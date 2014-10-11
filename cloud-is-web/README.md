#cloud-is


##CELAR Information System - Visualization Tool

###Prerequisites
To successfully install and use the CELAR Information System - Visualization Tool, Java (v1.7) and Apache Tomcat (v7) 
should be installed on your system.

For the  CELAR Information System - Visualization Tool to operate correctly the CELAR Information System - core must be
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

To install or update the CELAR Information System - Visualization Tool you have to issue
the following command

	yum update && yum install cloud-is-web

###Configuration
The CELAR Information System visualizationTool can be configured after its installation, by altering the files in

	{extracted_webapp_folder}/config

More specifically the property `issendpoint.ip` in the `init.properties` file should be set to the address that the
is-core (controllerModule) is deployed.

To ease the configuration process a user can user the scripts under the scripts folder

**config_ISendpoint.sh**
	
	Usage:
	-option1=value1 ... -optionX=valueX
		options:\\n
			-u=*    		The uri the IS Server listens for requests. The format must be http://{ip}:{post}/{REST_API_root}
			-p=*			Specifies the path the IS is installed