package crossway.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {

    /**
     * 设置属性
     *
     * @param bean
     *     对象
     * @param name
     *     属性名
     * @param clazz
     *     设置值的类
     * @param value
     *     属性值
     * @param <T>
     *     和值对应的类型
     *
     * @throws Exception
     *     设值异常
     */
    public static void setProperty(Object bean, String name, Class clazz, Object value) throws Exception {
        Method method = ReflectUtils.getPropertySetterMethod(bean.getClass(), name, clazz);
        if (method.isAccessible()) {
            method.invoke(bean, value);
        } else {
            try {
                method.setAccessible(true);
                method.invoke(bean, value);
            } finally {
                method.setAccessible(false);
            }
        }
    }

    /**
     * 得到属性的值
     *
     * @param bean
     *     对象
     * @param name
     *     属性名
     * @param clazz
     *     设置值的类
     * @param <T>
     *     和返回值对应的类型
     *
     * @return 属性值
     * @throws Exception
     *     取值异常
     */
    public static <T> T getProperty(Object bean, String name, Class<T> clazz) throws Exception {
        Method method = ReflectUtils.getPropertyGetterMethod(bean.getClass(), name);
        if (method.isAccessible()) {
            return (T) method.invoke(bean);
        } else {
            try {
                method.setAccessible(true);
                return (T) method.invoke(bean);
            } finally {
                method.setAccessible(false);
            }
        }
    }

    /**
     * 从一个对象复制相同字段到另一个对象，（只写有getter/setter方法都有的值）
     *
     * @param src
     *     原始对象
     * @param dst
     *     目标对象
     * @param ignoreFields
     *     忽略的字段
     */
    public static void copyProperties(Object src, Object dst, String... ignoreFields) {
        Class srcClazz = src.getClass();
        Class distClazz = dst.getClass();
        Method[] methods = distClazz.getMethods();
        List<String> ignoreFiledList = Arrays.asList(ignoreFields);
        for (Method dstMethod : methods) { // 遍历目标对象的方法
            if (Modifier.isStatic(dstMethod.getModifiers()) || !ReflectUtils.isBeanPropertyReadMethod(dstMethod)) {
                // 不是static方法， 是getter方法
                continue;
            }
            String propertyName = ReflectUtils.getPropertyNameFromBeanReadMethod(dstMethod);
            if (ignoreFiledList.contains(propertyName)) {
                // 忽略字段
                continue;
            }
            Class dstReturnType = dstMethod.getReturnType();
            try { // 同时目标字段还需要有set方法
                Method dstSetterMethod = ReflectUtils.getPropertySetterMethod(distClazz, propertyName, dstReturnType);
                if (dstSetterMethod != null) {
                    // 再检查原始对象方法
                    Method srcGetterMethod = ReflectUtils.getPropertyGetterMethod(srcClazz, propertyName);
                    // 原始字段有getter方法
                    Class srcReturnType = srcGetterMethod.getReturnType();
                    if (srcReturnType.equals(dstReturnType)) { // 原始字段和目标字段返回类型一样
                        Object val = srcGetterMethod.invoke(src); // 从原始对象读取值
                        if (val != null) {
                            dstSetterMethod.invoke(dst, val); // 设置到目标对象
                        }
                    }
                }
            } catch (Exception ignore) {
                // ignore 下一循环
            }
        }
    }

    public static Map<String, Object> copyBeanToMap(Object bean) {
        if (bean == null) {
            return null;
        }

        Map<String, Object> properties = new HashMap<>();
        Arrays.stream(bean.getClass().getMethods()).forEach(method -> {
            try {
                if (ReflectUtils.isBeanPropertyReadMethod(method)) {
                    String key = ReflectUtils.getPropertyNameFromBeanReadMethod(method);
                    properties.put(key, method.invoke(bean));
                }
            } catch (Exception ignore) {
            }
        });
        return properties;
    }

    public static void copyMapToBean(Object bean, Map<String, Object> properties) {
        if (CommonUtils.isEmpty(properties) || bean == null) {
            return;
        }

        properties.entrySet().forEach(entry -> {
            try {
                setProperty(bean, entry.getKey(), entry.getValue().getClass(), entry.getValue());
            } catch (Exception ignore) {
            }
        });
    }
}
