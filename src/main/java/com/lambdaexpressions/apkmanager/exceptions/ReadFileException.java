package com.lambdaexpressions.apkmanager.exceptions;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 11:23 PM
 */
public class ReadFileException extends PackageException {
  public ReadFileException(String message, String appName, String version) {
    super(message, appName, version);
  }
}
