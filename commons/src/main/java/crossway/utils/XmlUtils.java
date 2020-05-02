package crossway.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.InputStream;
import java.util.Map;

/**
 * xml 序列化工具
 *
 * @author Johnson Wang
 **/
public class XmlUtils {

    private static XmlMapper xmlMapper = new XmlMapper();

    static {
        commonConfig(xmlMapper);
    }

    private static void commonConfig(ObjectMapper om) {
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.setTimeZone(DateUtils.CCT_TIME_ZONE);
    }

    public static <T> T findValue(String jsonStr, String fieldName, Class<T> valueType) {
        return findValue(jsonStr, fieldName, valueType, xmlMapper);
    }

    public static <T> T findValue(String jsonStr, String fieldName, Class<T> valueType,
                                  ObjectMapper om) {
        try {
            JsonNode fieldNode = om.readTree(jsonStr).findValue(fieldName);
            return om.convertValue(fieldNode, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getValue(String xml, String... nodeKeys) {
        return JsonUtils.getValue(xml, xmlMapper, nodeKeys);
    }

    public static <T> T getValue2Bean(String xml, Class<T> valueType, String... nodeKeys) {
        return JsonUtils.getValue2Bean(xml, xmlMapper, valueType, nodeKeys);
    }

    public static Map<String, Object> toMap(String xmlInString) {
        return JsonUtils.toMap(xmlInString, xmlMapper);
    }

    public static Map<String, Object> toMap(InputStream inputStream) {
        return JsonUtils.toMap(inputStream, xmlMapper);
    }

    public static <T> T toObject(String xmlInString, Class<T> valueType) {
        return JsonUtils.toObject(xmlInString, valueType, xmlMapper);
    }

    public static <T> T toObject(InputStream inputStream, Class<T> valueType) {
        return JsonUtils.toObject(inputStream, valueType, xmlMapper);
    }

    public static String toString(Object jsonOutObj) {
        return JsonUtils.toString(jsonOutObj, xmlMapper);
    }
}
