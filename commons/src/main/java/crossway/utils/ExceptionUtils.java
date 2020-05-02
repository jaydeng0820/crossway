package crossway.utils;

import crossway.exception.CrossWayException;
import crossway.exception.CrossWayRuntimeException;

/**
 * 异常工具类
 */
public final class ExceptionUtils {

    public static CrossWayRuntimeException buildRuntime(String configKey, String configValue) {
        String msg = "The value of config " + configKey + " [" + configValue + "] is illegal, please check it";
        return new CrossWayRuntimeException(msg);
    }

    public static CrossWayRuntimeException buildRuntime(String configKey, String configValue, String message) {
        String msg = "The value of config " + configKey + " [" + configValue + "] is illegal, " + message;
        return new CrossWayRuntimeException(msg);
    }

    public static boolean isServerException(CrossWayException exception) {
        int errorType = exception.getErrorType();
        return errorType >= 100 && errorType < 200;
    }

    public static boolean isClientException(CrossWayException exception) {
        int errorType = exception.getErrorType();
        return errorType >= 200 && errorType < 300;
    }

    /**
     * 返回堆栈信息（e.printStackTrace()的内容）
     *
     * @param e Throwable
     * @return 异常堆栈信息
     */
    public static String toString(Throwable e) {
        StackTraceElement[] traces = e.getStackTrace();
        StringBuilder sb = new StringBuilder(1024);
        sb.append(e.toString()).append("\n");
        if (traces != null) {
            for (StackTraceElement trace : traces) {
                sb.append("\tat ").append(trace).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * 返回消息+简短堆栈信息（e.printStackTrace()的内容）
     *
     * @param e          Throwable
     * @param stackLevel 堆栈层级
     * @return 异常堆栈信息
     */
    public static String toShortString(Throwable e, int stackLevel) {
        StackTraceElement[] traces = e.getStackTrace();
        StringBuilder sb = new StringBuilder(1024);
        sb.append(e.toString()).append("\t");
        if (traces != null) {
            for (int i = 0; i < traces.length; i++) {
                if (i < stackLevel) {
                    sb.append("\tat ").append(traces[i]).append("\t");
                } else {
                    break;
                }
            }
        }
        return sb.toString();
    }
}
