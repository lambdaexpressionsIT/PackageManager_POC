package com.lambdaexpressions.apkmanager.v1.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 11:13 PM
 */
@Data
@Builder
public class ErrorDTO {
  private String appName;
  private String version;
  private String errorMessage;
}
