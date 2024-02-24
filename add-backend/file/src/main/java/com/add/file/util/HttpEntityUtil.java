package com.add.file.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.add.file.exception.config.ApiException;

public final class HttpEntityUtil {

  private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

  private HttpHeaders headers = new HttpHeaders();

  public static HttpEntityUtil builder() {
    return new HttpEntityUtil();
  }

  public HttpEntityUtil setContentType(MediaType mediaType) {
    if (mediaType == null) {
      throw new ApiException("NULL MediaType");
    }
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    return this;

  }

  public HttpEntityUtil addParameter(String paramName, Object paramValue) {
    if (paramValue == null) {
      throw new ApiException("NULL value parameter");
    }
    if (paramName == null) {
      throw new ApiException("NULL name parameter");
    }
    params.add(paramName, paramValue.toString());
    return this;

  }

  public HttpEntity<MultiValueMap<String, String>> build() {
    return new HttpEntity<>(params, headers);

  }

}
