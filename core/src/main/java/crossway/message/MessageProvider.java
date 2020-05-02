package crossway.message;

import crossway.ext.ExtensionClass;
import crossway.ext.ExtensionLoaderFactory;
import crossway.message.converter.MessageConverter;
import crossway.utils.BeanUtils;
import crossway.utils.ClassTypeUtils;
import crossway.utils.JsonUtils;
import crossway.utils.ReflectUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/28
 **/
public class MessageProvider {

    private Object requestSource;
    private Object responseSource;

    private MessageConverter requestConverter;
    private MessageConverter responseConverter;

    private MessageMetaData metaData;

    public void copyPropertyFromString(String message, MessageMetaData messageMetaData) {
        for (Method method : messageMetaData.getClass().getMethods()) {
            if (ReflectUtils.isBeanPropertyWriteMethod(method)) {
                String propertyName = ReflectUtils.getPropertyNameFromBeanWriteMethod(method);
                Object value = JsonUtils.findValue(message, propertyName, method.getReturnType());
                try {
                    method.invoke(messageMetaData, value);
                } catch (Exception ignore) {
                }
            }
        }
    }

    public <S, T extends Message> MessageConverter<S, T> getConverter(Class<S> source, Class<T> target) {
        ConcurrentMap<String, ExtensionClass<MessageConverter>> all = ExtensionLoaderFactory
                .getExtensionLoader(MessageConverter.class).getAllExtensions();

        for (ExtensionClass<MessageConverter> extensionClass : all.values()) {
            if (isMatch(source, target, extensionClass)) {
                return extensionClass.getExtInstance();
            }
        }

        for (ExtensionClass<MessageConverter> extensionClass : all.values()) {
            if (isMatch(source.getSuperclass(), target.getSuperclass(), extensionClass)) {
                return extensionClass.getExtInstance();
            }
        }

        for (ExtensionClass<MessageConverter> extensionClass : all.values()) {
            if (isMatch(source.getSuperclass(), target.getSuperclass(), extensionClass)) {
                return extensionClass.getExtInstance();
            }
        }

        for (ExtensionClass<MessageConverter> extensionClass : all.values()) {
            if (isMatch(source, target.getSuperclass(), extensionClass)) {
                return extensionClass.getExtInstance();
            }
        }

        for (ExtensionClass<MessageConverter> extensionClass : all.values()) {
            if (isMatch(source.getSuperclass(), target, extensionClass)) {
                return extensionClass.getExtInstance();
            }
        }

        throw new NullPointerException(
                "no find mattch converter source: “" + source.getName() + "” target: “" + target.getName()
                        + "”");
    }

    public MessageMetaData getMetaData() {
        if (this.metaData == null) {
            this.metaData = getMetaDataInternal();
        }
        return metaData;
    }

    protected MessageMetaData getMetaDataInternal() {
        MessageMetaData metaData = new MessageMetaData();

        if (getRequestSource() instanceof String || getRequestSource() instanceof Message) {
            copyPropertyFromString(this.requestSource.toString(), metaData);
        } else {
            BeanUtils.copyProperties(metaData, getRequestSource());
        }

        return metaData;
    }

    public <T extends Message> MessageConverter<?, T> getRequest(Class<T> targetType) {
        if (requestConverter == null) {
            this.requestConverter = getConverter(this.requestSource.getClass(), targetType);
            this.requestConverter.setSource(this.requestSource);
            this.requestConverter.setProvider(this);
        }
        return this.requestConverter;
    }

    public <T extends Message> MessageConverter<?, T> getResponse(Class<T> targetType) {
        if (responseConverter == null) {
            Class target = this.responseSource == null ? Object.class : this.responseSource.getClass();
            this.responseConverter = getConverter(target, targetType);
            this.responseConverter.setSource(this.responseSource);
            this.responseConverter.setProvider(this);
        }
        return this.responseConverter;
    }

    public Object getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(Object requestSource) {
        this.requestSource = requestSource;
    }

    public Object getResponseSource() {
        return responseSource;
    }

    public void setResponseSource(Object responseSource) {
        this.responseSource = responseSource;
    }

    public boolean isMatch(Class source, Class target,
                           ExtensionClass<MessageConverter> extensionClass) {
        if (source != null && target != null) {
            Class sourceClass = ClassTypeUtils.getActualTypeArgument(extensionClass.getClazz(), 0);
            Class targetClass = ClassTypeUtils.getActualTypeArgument(extensionClass.getClazz(), 1);
            boolean isSource = sourceClass.getName().equalsIgnoreCase(source.getName());
            boolean isTarget = targetClass.getName().equalsIgnoreCase(target.getName());
            return isSource && isTarget;
        }
        return false;
    }
}
