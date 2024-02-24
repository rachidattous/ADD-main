package com.add.auth.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.add.auth.exception.config.ApiException;

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

  public HttpEntityUtil setAuthorization(String token) {
    if (token == null) {
      throw new ApiException("NULL Token");
    }
    headers.add(HttpHeaders.AUTHORIZATION, token);
    return this;

  }

  public HttpEntityUtil addParameter(String pramName, Object parmValue) {
    if (parmValue == null) {
      throw new ApiException("NULL value parameter");
    }
    if (pramName == null) {
      throw new ApiException("NULL name parameter");
    }
    params.add(pramName, parmValue.toString());
    return this;

  }

  public HttpEntity<MultiValueMap<String, String>> build() {
    return new HttpEntity<>(params, headers);

  }

}
