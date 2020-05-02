package crossway.log;

import crossway.log.exception.LogCodeNotFoundException;
import crossway.log.exception.LogFormatException;
import crossway.utils.IOUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LogCodes {

    // 02999 common通用的
    // 未知错误
    public static final String LOCALFILEREGISTRY_FAIL_WRITEFILE = "029990001";
    protected static final Map<String, String> LOG_CODES = new ConcurrentHashMap<>();
    protected final static String LOG = "crossway-%s: %s";

    static {
        init("logcodes-common");
    }

    /**
     * 初始化 Log Codes
     *
     * @param filename 用户名
     */
    public static void init(String filename) {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader newClassLoader = LogCodes.class.getClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(newClassLoader);
            // 由于 ConfigUtil 类是在 sofa-rpc-api 工程里的，core 依赖了 log
            // 所以不能直接使用 ConfigUtil，以免导致循环依赖
            // 故直接获取环境变量
            String encoding = Locale.getDefault().toString();
            if (encoding == null || encoding.length() == 0) {
                encoding = Locale.ENGLISH.toString();
            }
            String name = "crossway/" + filename + "_" + encoding + ".properties";
            // 如果没有找到文件，默认读取 $filename_en.properties
            if (LogCodes.class.getClassLoader().getResource(name) == null) {
                name = "sofa-rpc/" + filename + "_" + Locale.ENGLISH.toString() + ".properties";
            }
            InputStreamReader reader = null;
            InputStream in = null;
            try {
                Properties properties = new Properties();
                in = LogCodes.class.getClassLoader().getResourceAsStream(name);
                reader = new InputStreamReader(in, "UTF-8");
                properties.load(reader);
                for (Map.Entry entry : properties.entrySet()) {
                    LOG_CODES.put((String) entry.getKey(), (String) entry.getValue());
                }
            } catch (Exception e) {
                log.error("初始化日志码失败：" + name, e);
            } finally {
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(reader);
            }
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }

    }

    public static String getLog(String code, Object... messages) {
        String message = LOG_CODES.get(code);

        if (message == null) {
            throw new LogCodeNotFoundException(code);
        }

        try {
            return String.format(LOG, code, MessageFormat.format(message, messages));
        } catch (Throwable e) {
            throw new LogFormatException(code);
        }
    }

    /**
     * 当输入为日志码的时候，输出日志码对应的日志内容
     * 否则直接输出日志内容
     *
     * @param codeOrMsg 日志码或日志输出
     * @return 基本日志输出，不包含日志码
     */
    public static String getLiteLog(String codeOrMsg, Object... messages) {
        String message = LOG_CODES.get(codeOrMsg);

        if (message == null) {
            return MessageFormat.format(codeOrMsg, messages);
        }

        try {
            return MessageFormat.format(message, messages);
        } catch (Throwable e) {
            throw new LogFormatException(codeOrMsg);
        }
    }
}
