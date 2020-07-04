package crossway.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    /**
     * The empty String {@code ""}.
     */
    public static final String EMPTY = "";

    /**
     * The context path separator String {@code "/"}.
     */
    public static final String CONTEXT_SEP = "/";

    /**
     * The string {@code "*"}.
     */
    public static final String ALL = "*";

    /**
     * The string {@code "default"}.
     */
    public static final String DEFAULT = "default";

    /**
     * The string {@code "true"}.
     */
    public static final String TRUE = "true";

    /**
     * The string {@code "false"}.
     */
    public static final String FALSE = "false";

    /**
     * The string {@code "null"}.
     */
    public static final String NULL = "null";

    /**
     * 空数组
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];


    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Returns either the passed in String,
     * or if the String is {@code null}, an empty String ("").</p>
     *
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString("")    = ""
     * StringUtils.defaultString("bat") = "bat"
     * </pre>
     *
     * @param str the String to check, may be null
     * @return the passed in String, or the empty String if it
     * was {@code null}
     * @see String#valueOf(Object)
     */
    public static String defaultString(final Object str) {
        return toString(str, EMPTY);
    }

    /**
     * 对象转string
     *
     * @param o          对象
     * @param defaultVal 默认值
     * @return 不为null执行toString方法
     */
    public static String toString(Object o, String defaultVal) {
        return o == null ? defaultVal : o.toString();
    }

    /**
     * 对象转string
     *
     * @param o 对象
     * @return 不为null执行toString方法
     */
    public static String toString(Object o) {
        return toString(o, null);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * 按分隔符分隔的数组，包含空值<br>
     * 例如 "1,2,,3," 返回 [1,2,,3,] 5个值
     *
     * @param src       原始值
     * @param separator 分隔符
     * @return 字符串数组
     */
    public static String[] split(String src, String separator) {
        if (isEmpty(separator)) {
            return new String[]{src};
        }
        if (isEmpty(src)) {
            return StringUtils.EMPTY_STRING_ARRAY;
        }
        return src.split(separator, -1);
    }

    /**
     * 按逗号或者分号分隔的数组，排除空字符<br>
     * 例如 " 1,2 ,, 3 , " 返回 [1,2,3] 3个值<br>
     * " 1;2 ;; 3 ; " 返回 [1,2,3] 3个值<br>
     *
     * @param src 原始值
     * @return 字符串数组
     */
    public static String[] splitWithCommaOrSemicolon(String src) {
        if (isEmpty(src)) {
            return StringUtils.EMPTY_STRING_ARRAY;
        }
        String[] ss = split(src.replace(',', ';'), ";");
        List<String> list = new ArrayList<String>();
        for (String s : ss) {
            if (!isBlank(s)) {
                list.add(s.trim());
            }
        }
        return list.toArray(new String[list.size()]);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !StringUtils.isEmpty(cs);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
