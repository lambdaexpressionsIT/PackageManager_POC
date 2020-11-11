package com.lambdaexpressions.apkmanager.exceptions;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 11:38 PM
 */
public class WriteFileException extends PackageException {
  public WriteFileException(String message, String appName, String version) { super(message, appName, version); }
}
