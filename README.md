# apkmanager

[![CircleCI](https://circleci.com/gh/steccothal/apkmanager.svg?style=shield&circle-token=3c19eba4a52062ce07b8ef82735f7d2217509c3a)](https://app.circleci.com/pipelines/github/steccothal/apkmanager)

RESTful interface to locally store apk packages sent via HTTP POST and send them when requested via HTTP GET.

## USE JAVA 9


## How to compile the code from the command line using maven
```sh
mvn clean install
```

### How to run the PackageManager from the command line using maven
```sh
mvn spring-boot:run
```

## Folder

All the files will be saved into the file system.
Change this resource file to change the path:  /src/main/resources/application.properties 


## POST example
http://someserver.com/api/v1/downloadApk/com.some.app/1
Request body:
```json
{
    "name": "com.some.app",
    "version": "1",
    "apkFile": "c29tZSBkdW1teSBieXRlcw=="
}
```
This will create a local folder tree from folder specified in filesystem.baselocation application property
${filesystem.baselocation}/com.some.app/1/com.some.app.apk                                                                                                                                                                                                                       

## GET example
http://someserver/api/v1/getApk/com.some.app/1
Response body:
```json
{
    "name": "com.some.app",
    "version": "1",
    "apkFile": "c29tZSBkdW1teSBieXRlcw=="
}
```
