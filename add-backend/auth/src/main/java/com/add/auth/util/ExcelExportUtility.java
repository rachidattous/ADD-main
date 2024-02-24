package com.add.auth.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;

import com.add.auth.constants.IEnum;
import com.add.auth.exception.IX.ExportingExcelException;

import jxl.CellView;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;

import jxl.write.Label;

import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;

@UtilityClass
@Slf4j
public class ExcelExportUtility {

  private static final WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);

  private static final WritableCellFormat times10 = new WritableCellFormat(times10pt);

  private static final WritableFont times12pt = new WritableFont(WritableFont.TIMES, 12);

  private static final WritableCellFormat times12 = new WritableCellFormat(times12pt);

  private static final WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD,
      false, UnderlineStyle.SINGLE);

  private static final WritableCellFormat timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);

  public void addCaption(WritableSheet sheet, int column, int row, String s, WritableCellFormat timesBoldUnderline) {
    try {
      sheet.addCell(new Label(column, row, s, timesBoldUnderline));
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ExportingExcelException();
    }

  }

  public <T> void addLabel(WritableSheet sheet, int column, int row, T s) {
    try {
      sheet.addCell(new Label(column, row, (s != null) ? s.toString() : "", times10));
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ExportingExcelException();
    }

  }

  public void addLabel(WritableSheet sheet, int column, int row, IEnum s) {
    try {
      sheet.addCell(new Label(column, row, (s != null) ? s.getValue().toString() : "", times10));
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ExportingExcelException();
    }

  }

  public void createHeader(WritableSheet sheet, List<String> header) {

    try {
      times12.setBackground(Colour.BROWN);
      times12.setAlignment(Alignment.CENTRE);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    CellView cv = new CellView();
    cv.setFormat(times12);
    cv.setFormat(timesBoldUnderline);
    cv.setAutosize(true);

    // Write a few headers
    AtomicInteger col = new AtomicInteger(0);
    header.stream().forEach(e -> addHeader(e, sheet, col, cv));

  }

  public void addHeader(String header, WritableSheet sheet, AtomicInteger col, CellView cv) {
    try {
      addCaption(sheet, col.get(), 0, header, timesBoldUnderline);
      sheet.setColumnView(col.get(), cv);
      col.getAndIncrement();

    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

}
