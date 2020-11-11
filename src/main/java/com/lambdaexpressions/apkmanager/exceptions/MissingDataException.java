package com.lambdaexpressions.apkmanager.exceptions;

/**
 * Created by steccothal
 * on Wednesday 11 November 2020
 * at 9:43 AM
 */
public class MissingDataException extends PackageException {
  public MissingDataException(String message, String appName, String version) {
    super(message, appName, version);
  }
}
