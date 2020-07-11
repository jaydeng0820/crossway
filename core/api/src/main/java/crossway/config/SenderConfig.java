package crossway.config;

import crossway.bootstrap.Bootstraps;
import crossway.bootstrap.SenderBootstrap;
import crossway.common.CrossWayConfigs;
import crossway.common.CrossWayOptions;
import crossway.send.Send;
import crossway.transport.Transport;
import crossway.utils.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author iamcyw
 **/
public class SenderConfig extends AbstractServiceConfig<SenderConfig> implements Serializable {

    private static final long serialVersionUID = 8117151692206736207L;

    protected List<String> filters = Arrays.asList("log");

    /**
     * 实际代理协议类型
     */
    protected String protocol;

    protected String serializerType;

    /**
     * 启动器
     */
    protected String bootstrap;

    protected SenderBootstrap senderBootstrap;

    protected Transport transport;

    public String getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(String bootstrap) {
        this.bootstrap = bootstrap;
    }

    public String getProtocol() {
        return StringUtils.isEmpty(protocol) ? CrossWayConfigs.getStringValue(CrossWayOptions.DEFAULT_SEND) : protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Send refer() {
        if (this.senderBootstrap == null) {
            this.senderBootstrap = Bootstraps.from(this);
        }
        return this.senderBootstrap.refer();
    }

    public void unRefer() {
        if (this.senderBootstrap != null) {
            this.senderBootstrap.unRefer();
        }
    }

    public List<String> getFilters() {
        return filters;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public String getSerializerType() {
        return serializerType;
    }

    public void setSerializerType(String serializerType) {
        this.serializerType = serializerType;
    }
}
