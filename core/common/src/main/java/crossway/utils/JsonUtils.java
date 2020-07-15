package crossway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json 序列化工具
 *
 * @author andy
 */
public class JsonUtils {

    private static ObjectMapper defaultObjectMapper = new ObjectMapper();

    static {
        commonConfig(defaultObjectMapper);
    }

    private static void commonConfig(ObjectMapper om) {
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.setTimeZone(DateUtils.CCT_TIME_ZONE);
        om.setDateFormat(new SimpleDateFormat(DateUtils.DATE_FORMAT_TIME));
    }

    public static <T> T findValue(String jsonStr, String fieldName, Class<T> valueType) {
        return findValue(jsonStr, fieldName, valueType, defaultObjectMapper);
    }

    public static <T> T findValue(String jsonStr, String fieldName, Class<T> valueType, ObjectMapper om) {
        try {
            JsonNode fieldNode = om.readTree(jsonStr).findValue(fieldName);
            return om.convertValue(fieldNode, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取JSON中的某一个值
     *
     * <pre>
     * 这个方法返回的是String格式。
     * 建议使用这个方法来方便地取得目标值，而不是转化为临时的Map对象后通过map.get("key")取得。
     * </pre>
     *
     * @param json
     *     目标JSON字符串
     * @param nodeKeys
     *     抵达目标节点所有的节点key或数组下标
     *
     * @return 目标值
     */
    public static String getValue(String json, String... nodeKeys) {
        return getValue(json, defaultObjectMapper, nodeKeys);
    }

    /**
     * 读取JSON中的某一个具体值
     *
     * <pre>
     * 这个方法返回的是String格式。
     * 建议使用这个方法来方便地取得目标值，而不是转化为临时的Map对象后通过map.get("key")取得。
     * </pre>
     *
     * @param json
     *     目标JSON字符串
     * @param nodeKeys
     *     抵达目标节点所有的节点key或数组下标
     *
     * @return 目标值
     */
    public static String getValue(String json, ObjectMapper om, String... nodeKeys) {
        try {
            JsonNode node = om.readTree(json);
            for (String nodeKey : nodeKeys) {
                if (node == null) {
                    return null;
                }
                node = node.get(nodeKey);
            }
            return node.asText();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String getValue(InputStream inputStream, String... nodeKeys) {
        return getValue(inputStream, defaultObjectMapper, nodeKeys);
    }

    public static String getValue(InputStream inputStream, ObjectMapper om, String... nodeKeys) {
        try {
            JsonNode node = om.readTree(inputStream);
            for (String nodeKey : nodeKeys) {
                if (node == null) {
                    return null;
                }
                node = node.get(nodeKey);
            }
            return node.asText();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T getValue2Bean(String json, Class<T> valueType, String... nodeKeys) {
        return getValue2Bean(json, defaultObjectMapper, valueType, nodeKeys);
    }

    public static <T> T getValue2Bean(String json, ObjectMapper om, Class<T> valueType, String... nodeKeys) {
        try {
            JsonNode node = om.readTree(json);
            for (String nodeKey : nodeKeys) {
                if (node == null) {
                    return null;
                }
                node = node.get(nodeKey);
            }
            return om.convertValue(node, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T getValue2Bean(InputStream inputStream, Class<T> valueType, String... nodeKeys) {
        return getValue2Bean(inputStream, defaultObjectMapper, valueType, nodeKeys);
    }

    public static <T> T getValue2Bean(InputStream inputStream, ObjectMapper om, Class<T> valueType,
                                      String... nodeKeys) {
        try {
            JsonNode node = om.readTree(inputStream);
            for (String nodeKey : nodeKeys) {
                if (node == null) {
                    return null;
                }
                node = node.get(nodeKey);
            }
            return om.convertValue(node, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> List<T> toList(String jsonInString, Class<T> valueType, ObjectMapper om) {
        try {
            JavaType javaType = om.getTypeFactory().constructParametricType(ArrayList.class, valueType);
            return om.readValue(jsonInString, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> List<T> toList(String jsonInString, Class<T> valueType) {
        return toList(jsonInString, valueType, defaultObjectMapper);
    }

    public static Map<String, Object> toMap(String jsonInString) {
        return toMap(jsonInString, defaultObjectMapper);
    }

    public static Map<String, Object> toMap(String jsonInString, ObjectMapper om) {
        return toMap(jsonInString, String.class, Object.class, om);
    }

    public static <K, V> Map<K, V> toMap(String jsonInString, Class<K> keyClass, Class<V> valueClass) {
        return toMap(jsonInString, keyClass, valueClass, defaultObjectMapper);
    }

    public static <V> Map<String, V> toMap(String jsonInString, Class<V> valueType) {
        return toMap(jsonInString, String.class, valueType);
    }

    public static <K, V> Map<K, V> toMap(String jsonInString, Class<K> keyClass, Class<V> valueClass, ObjectMapper om) {
        try {
            MapType javaType = om.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);
            return om.readValue(jsonInString, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Map<String, Object> toMap(InputStream inputStream) {
        return toMap(inputStream, defaultObjectMapper);
    }

    public static Map<String, Object> toMap(InputStream inputStream, ObjectMapper om) {
        return toMap(inputStream, String.class, Object.class, om);
    }

    public static <V> Map<String, V> toMap(InputStream inputStream, Class<V> valueClass, ObjectMapper om) {
        return toMap(inputStream, String.class, valueClass, om);
    }

    public static <K, V> Map<K, V> toMap(InputStream jsonInString, Class<K> keyClass, Class<V> valueClass,
                                         ObjectMapper om) {
        try {
            MapType javaType = om.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);
            return om.readValue(jsonInString, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <V> Map<String, V> toMap(InputStream jsonInString, Class<V> valueClass) {
        return toMap(jsonInString, valueClass, defaultObjectMapper);
    }

    public static <T> T toObject(String jsonInString, Class<T> valueType) {
        return toObject(jsonInString, valueType, defaultObjectMapper);
    }

    public static <T> T toObject(String jsonInString, Class<T> valueType, ObjectMapper om) {
        try {
            return om.readValue(jsonInString, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T toObject(InputStream inputStream, Class<T> valueType) {
        return toObject(inputStream, valueType, defaultObjectMapper);
    }

    public static <T> T toObject(InputStream inputStream, Class<T> valueType, ObjectMapper om) {
        try {
            return om.readValue(inputStream, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String toString(Object jsonOutObj) {
        return toString(jsonOutObj, defaultObjectMapper);
    }

    public static String toString(Object jsonOutObj, ObjectMapper om) {
        try {
            return om.writeValueAsString(jsonOutObj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 对象转换行格式化的Json
     *
     * @param jsonOutObj
     *     源对象
     *
     * @return
     */
    public static String toStringWithWrap(Object jsonOutObj) {
        ObjectMapper objectMapper = new ObjectMapper();
        commonConfig(objectMapper);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return toString(jsonOutObj, objectMapper);
    }
}
