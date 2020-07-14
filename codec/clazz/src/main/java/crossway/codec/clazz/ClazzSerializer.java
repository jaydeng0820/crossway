package crossway.codec.clazz;

import crossway.codec.Serializer;
import crossway.codec.node.Node;
import crossway.ext.api.Extension;
import crossway.impl.codec.node.ArrayNode;
import crossway.impl.codec.node.NodeFactory;
import crossway.impl.codec.node.ObjectNode;
import crossway.utils.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Extension("clazz")
public class ClazzSerializer implements Serializer {
    @Override
    public Node encode(Object object, Map<String, Object> context) {
        if (object == null) {
            return NodeFactory.instance.nullNode();
        }
        if (ReflectUtils.isPrimitives(object.getClass())) {
            if (object instanceof CharSequence || object instanceof Character) {
                return NodeFactory.instance.textNode(object.toString());
            } else if (object instanceof Number) {
                if (object instanceof Integer) {
                    return NodeFactory.instance.numberNode((Integer) object);
                } else if (object instanceof Long) {
                    return NodeFactory.instance.numberNode((Long) object);
                } else if (object instanceof Double) {
                    return NodeFactory.instance.numberNode((Double) object);
                }
            } else if (object instanceof Date) {
                return NodeFactory.instance.dateNode((Date) object);
            } else if (object.getClass().isArray()) {
                ArrayNode arrayNode = NodeFactory.instance.arrayNode();
                Object[] arrayObj = (Object[]) object;
                for (Object o : arrayObj) {
                    arrayNode.add(encode(o, context));
                }
                return arrayNode;
            }
        } else if (object instanceof List) {
            ArrayNode arrayNode = NodeFactory.instance.arrayNode();
            ((List) object).forEach(o -> {
                arrayNode.add(encode(o, context));
            });
            return arrayNode;
        } else {
            Map<String, Object> properties = new HashMap<>();
            if (object instanceof Map) {
                properties.putAll((Map<? extends String, ?>) object);
            } else {
                properties.putAll(BeanUtils.copyBeanToMap(object));
            }
            ObjectNode objectNode = NodeFactory.instance.objectNode();
            if (CommonUtils.isNotEmpty(properties)) {
                properties.forEach((s, o) -> {
                    objectNode.put(s, encode(o, context));
                });
                return objectNode;
            } else {
                return NodeFactory.instance.nullNode();
            }
        }
        return null;
    }

    @Override
    public Object decode(Node data, Map<String, Object> context) {
        if (CommonUtils.isEmpty(context)) {

        } else {
            Class classType = ClassUtils.forName(StringUtils.toString(context.get("root")));

            return decode(data, classType);
        }
        return null;
    }

    private Object decode(Node data, Class type) {
        if (data.isObject()) {
            if (type == Object.class || type == Map.class) {
                Map<Object, Object> map = new HashMap<>();
                data.fields().forEachRemaining(nodeEntry -> {
                    map.put(nodeEntry.getKey(), decode(nodeEntry.getValue(), Object.class));
                });
                if (CommonUtils.isNotEmpty(map)) {
                    return map;
                }
            } else {
                Object obj = ClassUtils.newInstance(type);
                for (Method dstMethod : type.getMethods()) {
                    if (Modifier.isStatic(dstMethod.getModifiers()) || !ReflectUtils.isBeanPropertyWriteMethod(
                        dstMethod)) {
                        // 不是static方法和setter
                        continue;
                    }
                    String propertyName = ReflectUtils.getPropertyNameFromBeanWriteMethod(dstMethod);
                    Node field = data.get(propertyName);

                    Type returnType = dstMethod.getGenericParameterTypes()[0];
                    Object value = null;
                    if (returnType instanceof Class) {
                        value = decode(field, (Class) returnType);
                    } else if (returnType instanceof ParameterizedType) {
                        Class rawType = (Class) ((ParameterizedType) returnType).getRawType();
                        if (rawType == Map.class) {
                            Class returnClass = (Class) ((ParameterizedType) returnType).getActualTypeArguments()[1];
                            Map<Object, Object> map = new HashMap<>();
                            field.fields().forEachRemaining(nodeEntry -> {
                                map.put(nodeEntry.getKey(), decode(nodeEntry.getValue(), returnClass));
                            });
                            if (CommonUtils.isNotEmpty(map)) {
                                value = map;
                            }
                        } else if (rawType == List.class) {
                            Class returnClass = (Class) ((ParameterizedType) returnType).getActualTypeArguments()[0];
                            List<Object> list = new ArrayList<>();
                            field.forEach(node -> {
                                list.add(decode(node, returnClass));
                            });
                            if (CommonUtils.isNotEmpty(list)) {
                                value = list;
                            }
                        }
                    }

                    if (value != null) {
                        try {
                            if (dstMethod.isAccessible()) {
                                dstMethod.invoke(obj, value);
                            } else {
                                try {
                                    dstMethod.setAccessible(true);
                                    dstMethod.invoke(obj, value);
                                } finally {
                                    dstMethod.setAccessible(false);
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                return obj;
            }

        }
        if (ReflectUtils.isPrimitives(type)) {
            if (type == String.class) {
                return data.asText();
            } else if (Integer.class == type) {
                return data.asInt();
            } else if (type == Long.class) {
                return data.asLong();
            } else if (data.isBoolean() && type == Boolean.class) {
                return data.booleanValue();
            } else if (data.isDate() && type == Date.class) {
                return data.dateValue();
            }
        } else if (type == Object.class) {
            if (data.isTextual()) {
                return data.asText();
            } else if (data.isInt()) {
                return data.asInt();
            } else if (data.isLong()) {
                return data.isLong();
            } else if (data.isBoolean()) {
                return data.asBoolean();
            } else if (data.isDate()) {
                return data.dateValue();
            } else if (data.isArray()) {
                List<Object> list = new ArrayList<>();
                data.forEach(node -> {
                    list.add(decode(node, Object.class));
                });
                if (CommonUtils.isNotEmpty(list)) {
                    return list;
                }
            } else if (data.isObject()) {
                Map<String, Object> map = new HashMap<>();
                data.fields().forEachRemaining(nodeEntry -> {
                    map.put(nodeEntry.getKey(), decode(nodeEntry.getValue(), Object.class));
                });
                if (CommonUtils.isNotEmpty(map)) {
                    return map;
                }
            }
        }
        return null;
    }

}
