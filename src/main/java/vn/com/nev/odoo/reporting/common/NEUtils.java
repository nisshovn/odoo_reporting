package vn.com.nev.odoo.reporting.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class NEUtils {
  /**
   * Format date to string with specific pattern
   */
  public static String formatDate(Date date, String pattern) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    dateFormat.setLenient(false);

    return dateFormat.format(date);
  }

  public static int getInt(Map<String, Object> dataRow, ColumnNames columnName) {
    return Integer.parseInt(dataRow.get(columnName.toString()).toString());
  }

  public static String getString(Map<String, Object> dataRow, ColumnNames columnName) {
    return (String) dataRow.get(columnName.toString());
  }
}
