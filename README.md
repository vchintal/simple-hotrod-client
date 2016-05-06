# Simple Hotrod Client 

## Prerequisites
* JDK 1.8 
* Maven 3.3.9 
* __JAVA_HOME__ variable set 
* JAVA\_HOME\bin on the __$PATH__
* [JDG 6.6.0 server binary](https://www.jboss.org/download-manager/file/jboss-datagrid-6.6.0-server.zip) downloaded and unzipped. The location where the binary is unzipped would be referred to as __$JDG__ going further.

## Setup 

__Step 1__: Start four instances of JDG servers using the following commands run in __$JDG\bin__ folder:

```sh 
./clustered.sh -b 127.0.0.1 -Djboss.node.name=jdg1 -Djboss.default.multicast.address=224.1.1.1
./clustered.sh -b 127.0.0.1 -Djboss.socket.binding.port-offset=100 -Djboss.node.name=jdg2  -Djboss.default.multicast.address=224.1.1.1
./clustered.sh -b 127.0.0.1 -Djboss.socket.binding.port-offset=200 -Djboss.node.name=jdg3  -Djboss.default.multicast.address=224.1.1.1
./clustered.sh -b 127.0.0.1 -Djboss.socket.binding.port-offset=300 -Djboss.node.name=jdg4  -Djboss.default.multicast.address=224.1.1.1
```

__Step 2__: Execute the project with the following command:

```sh 
mvn clean package exec:exec -s settings.xml
```
