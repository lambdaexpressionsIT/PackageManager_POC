package com.lambdaexpressions.apkmanager.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 6:18 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APKMessageDTO {
  String name;
  String version;
  byte[] apkFile;
}
