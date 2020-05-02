package crossway.message.converter;

import crossway.ext.api.Extensible;
import crossway.message.Message;
import crossway.message.MessageProvider;
import crossway.utils.ClassTypeUtils;
import crossway.utils.JsonUtils;
import crossway.utils.StringUtils;
import crossway.utils.XmlUtils;


/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/29
 **/
@Extensible(singleton = false)
public abstract class MessageConverter<S, T extends Message> {

    private S source;
    private T target;
    private MessageProvider provider;

    public MessageProvider getProvider() {
        return provider;
    }

    public void setProvider(MessageProvider provider) {
        this.provider = provider;
    }

    public S getSource() {
        return source;
    }

    public void setSource(S source) {
        this.source = source;
        this.target = null;
    }

    public Class<S> getSourceClass() {
        return (Class<S>) ClassTypeUtils.getActualTypeArgument(this.getClass(), 0);
    }

    public T getTarget() {
        if (this.target == null) {
            this.target = getTargetInternal();
        }
        return target;
    }

    public Class<T> getTargetClass() {
        return (Class<T>) ClassTypeUtils.getActualTypeArgument(this.getClass(), 1);
    }

    public T getTargetInternal() {
        if (StringUtils.equalsIgnoreCase(getSourceClass().getName(), String.class.getName())) {
            return toTargetMix((String) getSource());
        } else if (StringUtils.equalsIgnoreCase(getSourceClass().getName(),
                getTargetClass().getName())) {
            return (T) this.source;
        }
        return null;
    }

    public <A> A toObjectMix(String source, Class<A> objectType) {
        try {
            return JsonUtils.toObject(source, objectType);
        } catch (Exception e) {
        }

        if (this.target == null) {
            try {
                return XmlUtils.toObject(source, objectType);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public T toTargetMix(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        return toObjectMix(source, getTargetClass());
    }
}
