package com.add.file.util;

import org.apache.commons.lang.text.StrSubstitutor;

import com.add.file.exception.config.ApiException;

import java.util.HashMap;
import java.util.Map;

public final class StringTemplate {

  private String url;

  private Map<String, String> params = new HashMap<>();

  public StringTemplate(String url) {
    this.url = url;
  }

  public static StringTemplate template(String urlTemplate) {
    if (urlTemplate == null) {
      throw new ApiException("NULL template");
    }
    return new StringTemplate(urlTemplate);
  }

  public StringTemplate addParameter(String pramName, Object parmValue) {
    if (parmValue == null) {
      throw new ApiException("NULL value parameter");
    }
    if (pramName == null) {
      throw new ApiException("NULL name parameter");
    }
    params.put(pramName, parmValue.toString());
    return this;

  }

  public String build() {
    return new StrSubstitutor(params).replace(url);

  }

}
