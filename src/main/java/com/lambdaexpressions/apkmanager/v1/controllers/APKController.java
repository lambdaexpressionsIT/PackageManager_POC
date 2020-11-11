package com.lambdaexpressions.apkmanager.v1.controllers;

import com.lambdaexpressions.apkmanager.exceptions.*;
import com.lambdaexpressions.apkmanager.services.APKService;
import com.lambdaexpressions.apkmanager.v1.model.APKMessageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 6:02 PM
 */
@RestController
@RequestMapping("api/v1/")
public class APKController {

  APKService apkService;

  public APKController(APKService apkService) {
    this.apkService = apkService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("downloadApk/{appName}/{version}")
  public void installPackageIF(@PathVariable String appName, @PathVariable String version, @RequestBody APKMessageDTO apkMessageDTO) throws WriteFileException {
    apkMessageDTO.setName(appName);
    apkMessageDTO.setVersion(version);
    this.installPackage(apkMessageDTO);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("downloadApk/{appName}")
  public void installPackageIF(@PathVariable String appName, @RequestBody APKMessageDTO apkMessageDTO) throws WriteFileException, MissingDataException {

    if (StringUtils.isBlank(apkMessageDTO.getVersion())) {
      throw new MissingDataException(PackageException.MISSING_VERSION_NUMBER_ERROR_MESSAGE, appName, apkMessageDTO.getVersion());
    }

    apkMessageDTO.setName(appName);
    this.installPackage(apkMessageDTO);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("downloadApk")
  public void installPackageIF(@RequestBody APKMessageDTO apkMessageDTO) throws WriteFileException, MissingDataException {
    if (StringUtils.isBlank(apkMessageDTO.getName())) {
      throw new MissingDataException(PackageException.MISSING_APP_NAME_ERROR_MESSAGE, apkMessageDTO.getName(), apkMessageDTO.getVersion());
    } else if (StringUtils.isBlank(apkMessageDTO.getVersion())) {
      throw new MissingDataException(PackageException.MISSING_VERSION_NUMBER_ERROR_MESSAGE, apkMessageDTO.getName(), apkMessageDTO.getVersion());
    }

    this.installPackage(apkMessageDTO);
  }

  private void installPackage(APKMessageDTO apkMessageDTO) throws WriteFileException {
    this.apkService.saveAPK(apkMessageDTO);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("getApk/{appName}/{version}")
  public APKMessageDTO getPackage(@PathVariable String appName, @PathVariable String version) throws ReadFileException, NotFoundException {
    return this.apkService.getAPKMessageByAppNameAndVersion(appName, version);
  }

}
