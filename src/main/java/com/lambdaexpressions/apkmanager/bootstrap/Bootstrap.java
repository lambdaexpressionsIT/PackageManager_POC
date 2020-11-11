package com.lambdaexpressions.apkmanager.bootstrap;

import com.lambdaexpressions.apkmanager.services.APKService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 6:42 PM
 */
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {
  @Value("${filesystem.baselocation}")
  String baseLocation;

  @Value("${filesystem.package.filextension}")
  String fileExtension;

  APKService apkService;

  public Bootstrap(APKService apkService) {
    this.apkService = apkService;
  }

  @Override
  public void run(String... args) {
    Map<String, Map<String, Boolean>> packagesMap = new HashMap<>();
    File baseDir = new File(baseLocation);

    Stream.ofNullable(baseDir.list((current, name) -> new File(current, name).isDirectory()))
        .forEach(dirArray -> Stream.of(dirArray).forEach(appName -> {
          final String appNameDirPath = baseLocation + File.separator + appName;
          File appNameDir = new File(appNameDirPath);
          if (!packagesMap.containsKey(appName)) {
            packagesMap.put(appName, new HashMap<>());
          }
          Map<String, Boolean> currentAppMap = packagesMap.get(appName);

          Stream.ofNullable(appNameDir.list((currentAppNameDir, currentAppName) -> new File(currentAppNameDir, currentAppName).isDirectory()))
              .forEach(appNameDirArray -> Stream.of(appNameDirArray).forEach(versionNumber -> {
                final String versionNumberDirPath = appNameDirPath + File.separator + versionNumber;
                File versionNumberDir = new File(versionNumberDirPath);
                if (!currentAppMap.containsKey(versionNumber)) {
                  currentAppMap.put(versionNumber, Boolean.FALSE);
                }

                Stream.ofNullable(versionNumberDir.list((currentVersionNumberDir, currentVersionNumber) -> new File(currentVersionNumberDir, currentVersionNumber).isFile()))
                    .forEach(filesArray -> Stream.of(filesArray).forEach(fileName -> {
                      if (fileName.equalsIgnoreCase(appName + fileExtension)) {
                        currentAppMap.put(versionNumber, Boolean.TRUE);
                      }
                    }));
              }));
        }));
    this.apkService.setUpDataSourceMap(packagesMap);
  }
}
