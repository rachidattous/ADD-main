package com.add.auth.exception.IX;

import com.add.auth.exception.config.ApiException;

public class ExportingExcelException extends ApiException {

  public ExportingExcelException() {
    super("error exporting the excel file");

  }

}
