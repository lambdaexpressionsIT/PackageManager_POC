# apkmanager

[![CircleCI](https://circleci.com/gh/steccothal/apkmanager.svg?style=shield&circle-token=3c19eba4a52062ce07b8ef82735f7d2217509c3a)](https://app.circleci.com/pipelines/github/steccothal/apkmanager)

RESTful interface to locally store apk packages sent via HTTP POST and send them when requested via HTTP GET.


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
