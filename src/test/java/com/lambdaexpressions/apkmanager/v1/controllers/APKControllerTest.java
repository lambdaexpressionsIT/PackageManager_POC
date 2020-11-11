package com.lambdaexpressions.apkmanager.v1.controllers;

import com.lambdaexpressions.apkmanager.exceptions.MissingDataException;
import com.lambdaexpressions.apkmanager.exceptions.PackageException;
import com.lambdaexpressions.apkmanager.helpers.TestHelper;
import com.lambdaexpressions.apkmanager.exceptions.NotFoundException;
import com.lambdaexpressions.apkmanager.services.APKService;
import com.lambdaexpressions.apkmanager.v1.RESTExceptionHandler;
import com.lambdaexpressions.apkmanager.v1.model.APKMessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 9:30 PM
 */
@ExtendWith(MockitoExtension.class)
class APKControllerTest {

  private final String DUMMY_APP_VERSION = "1";
  private final String DUMMY_APP_NAME = "it.appName";
  private final byte[] DUMMY_APK = DUMMY_APP_NAME.getBytes();

  @Mock
  APKService apkService;

  @InjectMocks
  APKController apkController;

  MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(apkController)
              .setControllerAdvice(new RESTExceptionHandler())
              .build();
  }

  @Test
  public void testFullPathDownloadAPK() throws Exception {
    //given
    APKMessageDTO apkMessage = APKMessageDTO.builder().apkFile(DUMMY_APK).build();

    //when

    //then
    mockMvc.perform(post(String.format("/api/v1/downloadApk/%s/%s", DUMMY_APP_NAME, DUMMY_APP_VERSION))
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestHelper.asJsonString(apkMessage)))
        .andExpect(status().isCreated());
  }

  @Test
  public void testAppNamePathDownloadAPK() throws Exception {
    //given
    APKMessageDTO apkMessage = APKMessageDTO.builder()
                                .apkFile(DUMMY_APK)
                                .version(DUMMY_APP_VERSION).build();
    //when

    //then
    mockMvc.perform(post(String.format("/api/v1/downloadApk/%s", DUMMY_APP_NAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestHelper.asJsonString(apkMessage)))
        .andExpect(status().isCreated());
  }

  @Test
  public void testVoidPathDownloadAPK() throws Exception{
    //given
    APKMessageDTO apkMessage = APKMessageDTO.builder()
                                .version(DUMMY_APP_VERSION)
                                .apkFile(DUMMY_APK)
                                .name(DUMMY_APP_NAME).build();

    //when

    //then
    mockMvc.perform(post("/api/v1/downloadApk")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestHelper.asJsonString(apkMessage)))
        .andExpect(status().isCreated());
  }

  @Test
  public void testGetAPK() throws Exception{
    //given
    APKMessageDTO apkMessage = APKMessageDTO.builder()
        .version(DUMMY_APP_VERSION)
        .apkFile(DUMMY_APK)
        .name(DUMMY_APP_NAME).build();

    given(apkService.getAPKMessageByAppNameAndVersion(anyString(), anyString())).willReturn(apkMessage);

    //when

    //then
    mockMvc.perform(get(String.format("/api/v1/getApk/%s/%s", DUMMY_APP_NAME, DUMMY_APP_VERSION)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(DUMMY_APP_NAME)))
        .andExpect(jsonPath("$.version", equalTo(DUMMY_APP_VERSION)));
  }

  @Test
  public void testGetNotFoundAPK() throws Exception {
    //given

    //when
    when(apkService.getAPKMessageByAppNameAndVersion(anyString(), anyString())).thenThrow(NotFoundException.class);

    //then
    mockMvc.perform(get(String.format("/api/v1/getApk/%s/%s", DUMMY_APP_NAME, DUMMY_APP_VERSION)))
        .andExpect(status().isNotFound())
        .andExpect(result-> assertTrue(result.getResolvedException() instanceof NotFoundException));
  }

  @Test
  public void testGetMissingAllData() throws Exception {
    //given
    APKMessageDTO apkMessage = APKMessageDTO.builder().apkFile(DUMMY_APK).build();

    //when
    //when(apkService.getAPKMessageByAppNameAndVersion(anyString(), anyString())).thenThrow(NotFoundException.class);

    //then
    mockMvc.perform(post("/api/v1/downloadApk")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestHelper.asJsonString(apkMessage)))
        .andExpect(status().isBadRequest())
        .andExpect(result->assertTrue(result.getResolvedException() instanceof MissingDataException))
        .andExpect(result -> assertTrue((result.getResolvedException().getMessage().equals(PackageException.MISSING_APP_NAME_ERROR_MESSAGE))));
  }

  @Test
  public void testGetMissingVersion() throws Exception {
    //given
    APKMessageDTO apkMessage = APKMessageDTO.builder()
        .apkFile(DUMMY_APK)
        .name(DUMMY_APP_NAME).build();

    //when
    //when(apkService.getAPKMessageByAppNameAndVersion(anyString(), anyString())).thenThrow(NotFoundException.class);

    //then
    mockMvc.perform(post("/api/v1/downloadApk")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestHelper.asJsonString(apkMessage)))
        .andExpect(status().isBadRequest())
        .andExpect(result->assertTrue(result.getResolvedException() instanceof MissingDataException))
        .andExpect(result -> assertTrue((result.getResolvedException().getMessage().equals(PackageException.MISSING_VERSION_NUMBER_ERROR_MESSAGE))));
  }

}