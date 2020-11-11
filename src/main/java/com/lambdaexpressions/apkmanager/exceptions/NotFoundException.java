package com.lambdaexpressions.apkmanager.exceptions;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 10:34 PM
 */
public class NotFoundException extends PackageException {
  public NotFoundException(String message, String appName, String version) {
    super(message, appName, version);
  }
}
