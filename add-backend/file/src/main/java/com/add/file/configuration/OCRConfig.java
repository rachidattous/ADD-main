package com.add.file.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import java.io.IOException;
import java.util.Properties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OCRConfig {

  @Value("${tesseract.path}")
  private String tesseractPath;

  @Bean
  public ITesseract getITesseract() throws IOException {
    ITesseract tesseract = new Tesseract();
    tesseract.setLanguage("eng+fra");
    tesseract.setDatapath(tesseractPath);
    return tesseract;
  }

  public String loadOCRPath() {
    try {
      ClassPathResource resource = new ClassPathResource("static/");
      return resource.getFile().getAbsolutePath();
    } catch (Exception e) {
      log.error(e.getMessage());
      return "";

    }

  }

}
