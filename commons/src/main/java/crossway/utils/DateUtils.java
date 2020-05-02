package crossway.utils;

import java.util.TimeZone;

public class DateUtils {

    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT24 = "YYYY/MM/DD HH24:MI:SS";
    public static final String DATE_FORMAT4D = "YYYY/MM/DD";
    public static final String DATE_FORMAT4DATE = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT4DAY = "yyyy/MM/dd";
    public static final String DATE_FORMAT4TIME = "HH:mm:ss";
    public static final String DATE_FORMAT4DAYD = "yyyy-MM-dd";
    public static final String DATE_FORMAT4NOSPLICING = "yyyyMMddHHmmss";

    /**
     * The Beijing
     */
    public static final TimeZone CCT_TIME_ZONE = TimeZone.getTimeZone("GMT+08:00");
}
