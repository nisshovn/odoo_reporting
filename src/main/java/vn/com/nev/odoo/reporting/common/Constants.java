package vn.com.nev.odoo.reporting.common;

public class Constants {

  public static final String APPLICATION_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


  /**
   * Date formats
   */
  public final class DateFormat {

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD_HHMM = "yyyyMMdd_HHmm";
    public static final String YYYYMMDD_HHMMSS_SSS = "yyyy/MM/dd HH:mm:ss.SSS";
    public static final String YYYY_MM_DD = "yyyy/MM/dd";
    public static final String MM_YYYY = "MM/yyyy";
    public static final String YYYY_MM_DD_T_HH_MM_SS_SSS = "yyyy/MM/dd'T'HH:mm:ss.SSS";

    /**
     * November 7, 2018, 12:25 PM
     */
    public static final String MMMM_D_YYYY_H_M_A = "MMMM d, YYYY, h:m a";

    private DateFormat() {
    }
  }
}
