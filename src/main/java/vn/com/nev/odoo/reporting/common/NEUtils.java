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

    public static int getInt(Map<String, Object> dataRow, Enum columnName) {
        return Integer.parseInt(dataRow.get(columnName.toString()).toString());
    }

    public static String getString(Map<String, Object> dataRow, Enum columnName) {
        return (String) dataRow.get(columnName.toString());
    }

//    public static Date parseDate(Map<String, Object> dataRow, Enum columnName) {
//        String sDate = (String) dataRow.get(columnName.toString());
//        if (!Strings.IsNullOrEmpty(sDate))
//            return new SimpleDateFormat(Constants.DateFormat.YYYY_MM_DD).parse(sDate);
//    }
}
