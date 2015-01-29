#cloud-is


##CELAR Information System - Core

###Prerequisites
To successfully install and use the CELAR Information System - Core, Java (v1.7) should be installed on your system 

For the  CELAR Information System - Core to operate correctly the CELAR Server Module must be
installed also and be accessible from the CELAR Information System.

###Installation

For installing using the rpm distribution, the repository must be defined first 
under the `/etc/yum.repos.d/` (e.g. CELAR.repo) with the following content:

```Bash
[CELAR-snapshots]
name=CELAR-snapshots
baseurl=http://snf-175960.vm.okeanos.grnet.gr/nexus/content/repositories/snapshots
enabled=1
protect=0
gpgcheck=0
metadata_expire=30s
autorefresh=1
type=rpm-md
```

To install or update the CELAR Information System - Core you have to issue
the following command

```Bash
yum update && yum install cloud-is-core
```

###Configuration
To configure CELAR Information System Core (controllerModule) a user must alter the files in

    /usr/local/bin/celarISServerDir/resources/config

The file `server.properties` contains the initialization and configuration values of the Inforamtion System Service. More specifically the property `common.collector` needs to be set to `celar` (which is the default value) if the Information System is installed under the CELAR umbrella or it should be set to `dunmmy` if someone wants to run Information System in a standalone mode e.g. for testing purposes. While the `mode` is set to `dummy` the dataCollection module generates random data to showcase the Information System functionality.
The property `srv.port`, in the same configuration file, indicates the port where the service listens for Rest API Calls. 

In a second step the properties at the path

    /usr/local/bin/celarISServerDir/resources/config/celar/endpoint.celarmanager.properties
	
need to be set to the correct CELAR Manager url parameters

