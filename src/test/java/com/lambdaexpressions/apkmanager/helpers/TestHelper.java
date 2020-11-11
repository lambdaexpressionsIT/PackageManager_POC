package com.lambdaexpressions.apkmanager.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by steccothal
 * on Wednesday 11 November 2020
 * at 11:11 AM
 */
public class TestHelper {

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
