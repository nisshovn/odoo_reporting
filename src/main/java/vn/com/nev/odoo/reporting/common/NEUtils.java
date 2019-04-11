package vn.com.nev.odoo.reporting.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NEUtils {
  /**
   * Format date to string with specific pattern
   */
  public static String formatDate(Date date, String pattern) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    dateFormat.setLenient(false);

    return dateFormat.format(date);
  }
}
