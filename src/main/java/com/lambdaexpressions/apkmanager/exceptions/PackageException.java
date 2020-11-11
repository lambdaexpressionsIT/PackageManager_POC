package com.lambdaexpressions.apkmanager.exceptions;

import lombok.Getter;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 11:22 PM
 */
@Getter
public class PackageException extends Exception{

  public static final String NOT_FOUND_ERROR_MESSAGE = "Package not installed";
  public static final String IO_READ_ERROR_MESSAGE = "Unreadable package file";
  public static final String IO_WRITE_ERROR_MESSAGE = "Cannot install package file";
  public static final String MISSING_APP_NAME_ERROR_MESSAGE = "No app name specified, impossible to locate resource";
  public static final String MISSING_VERSION_NUMBER_ERROR_MESSAGE = "No version number specified, impossible to locate resource";

  String appName;
  String version;

  public PackageException(String message, String appName, String version) {
    super(message);
    this.appName = appName;
    this.version = version;
  }
}
