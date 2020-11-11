package com.lambdaexpressions.apkmanager.services.impl;

import com.lambdaexpressions.apkmanager.exceptions.NotFoundException;
import com.lambdaexpressions.apkmanager.exceptions.PackageException;
import com.lambdaexpressions.apkmanager.exceptions.ReadFileException;
import com.lambdaexpressions.apkmanager.exceptions.WriteFileException;
import com.lambdaexpressions.apkmanager.services.APKService;
import com.lambdaexpressions.apkmanager.v1.model.APKMessageDTO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 6:39 PM
 */
@Service
public class APKServiceImpl implements APKService {

  Map<String, Map<String, Boolean>> appVersionMap;

  @Value("${filesystem.package.filextension}")
  String fileExtension;

  @Value("${filesystem.baselocation}")
  String baseLocation;

  public void setUpDataSourceMap(Map<String, Map<String, Boolean>> appVersionMap) {
    this.appVersionMap = appVersionMap;
  }

  public APKMessageDTO getAPKMessageByAppNameAndVersion(String appName, String version) throws ReadFileException, NotFoundException {
    if (appVersionMap.containsKey(appName) && appVersionMap.get(appName).containsKey(version)) {
      String filePath = this.getFilePath(appName, version);
      APKMessageDTO apkMessageDTO = APKMessageDTO.builder().name(appName).version(version).build();

      try {
        byte[] apkByteArray = FileUtils.readFileToByteArray(new File(filePath));
        apkMessageDTO.setApkFile(apkByteArray);
      } catch (IOException e) {
        throw new ReadFileException(PackageException.IO_READ_ERROR_MESSAGE, appName, version);
      }

      return apkMessageDTO;
    } else {
      throw new NotFoundException(PackageException.NOT_FOUND_ERROR_MESSAGE, appName, version);
    }
  }

  public void saveAPK(APKMessageDTO apkMessageDTO) throws WriteFileException {
    String filePath = this.getFilePath(apkMessageDTO.getName(), apkMessageDTO.getVersion());

    try {
      FileUtils.writeByteArrayToFile(new File(filePath), apkMessageDTO.getApkFile());
      Map<String, Boolean> versionMap = new HashMap<>();
      versionMap.put(apkMessageDTO.getVersion(), Boolean.TRUE);
      appVersionMap.put(apkMessageDTO.getName(), versionMap);
    } catch (IOException e) {
      throw new WriteFileException(PackageException.IO_WRITE_ERROR_MESSAGE, apkMessageDTO.getName(), apkMessageDTO.getVersion());
    }
  }

  private String getFilePath(String appName, String version) {
    return String.format("%s%s%s%s%s%s%s%s", this.baseLocation, File.separator, appName, File.separator, version, File.separator, appName, fileExtension);
  }

}
