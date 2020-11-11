package com.lambdaexpressions.apkmanager.services;

import com.lambdaexpressions.apkmanager.exceptions.NotFoundException;
import com.lambdaexpressions.apkmanager.exceptions.ReadFileException;
import com.lambdaexpressions.apkmanager.exceptions.WriteFileException;
import com.lambdaexpressions.apkmanager.v1.model.APKMessageDTO;

import java.util.Map;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 9:25 PM
 */
public interface APKService {

  void setUpDataSourceMap(Map<String, Map<String, Boolean>> appVersionMap);

  APKMessageDTO getAPKMessageByAppNameAndVersion(String appName, String version) throws ReadFileException, NotFoundException;

  void saveAPK(APKMessageDTO apkMessageDTO) throws WriteFileException;
}
