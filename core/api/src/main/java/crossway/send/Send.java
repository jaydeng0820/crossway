package crossway.send;

import crossway.codec.Serializer;
import crossway.codec.SerializerFactory;
import crossway.config.SenderConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.exception.CrossWayException;
import crossway.ext.api.Extensible;
import crossway.utils.StringUtils;

import java.util.UUID;

/**
 * @author iamcyw
 **/
@Extensible
public abstract class Send {

    private final SenderConfig senderConfig;

    private final int id = UUID.randomUUID().hashCode();

    protected Send(SenderConfig senderConfig) {
        this.senderConfig = senderConfig;
    }

    public SenderConfig getConfig() {
        return senderConfig;
    }

    protected String getSerializeType() {
        return StringUtils.isNotEmpty(getConfig().getSerializerType()) ? getConfig().getSerializerType() :
               getDefaultSerializeType();
    }

    protected abstract String getDefaultSerializeType();

    protected Serializer getSerializer() {
        return SerializerFactory.getSerializer(getSerializeType());
    }

    public int getId() {
        return id;
    }

    /**
     * 执行调用
     *
     * @param request
     *     请求
     *
     * @return CrossWayRequest 响应
     * @throws CrossWayException
     *     rpc异常
     */
    public abstract CrossWayResponse invoke(CrossWayRequest request) throws CrossWayException;
}
