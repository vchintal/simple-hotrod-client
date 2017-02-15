# Simple Hotrod Client 

## Prerequisites
* JDK 1.8 
* Maven 3.3.9 
* __JAVA_HOME__ variable set 
* JAVA\_HOME\bin on the __$PATH__
* [JDG 7.0 server binary](https://developers.redhat.com/download-manager/file/jboss-datagrid-7.0.0-library.zip) downloaded and unzipped. The location where the binary is unzipped would be referred to as __$JDG_HOME__ going further.

## Setup 

__Step 1__: Start JDG 7.0 in domain mode using the following command run in __$JDG_HOME\bin__ folder:

```sh 
./domain.sh
```

Using the management console of JDG 7.0 add a minimum of total 3 servers.

__Step 2__: Execute the project with the following command:

```sh 
mvn clean package exec:exec 
```
