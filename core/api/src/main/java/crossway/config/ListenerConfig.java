package crossway.config;

import crossway.bootstrap.Bootstraps;
import crossway.bootstrap.ListenerBootstrap;
import crossway.common.CrossWayConfigs;
import crossway.common.CrossWayOptions;
import crossway.listen.Listener;
import crossway.transport.Transport;
import crossway.utils.StringUtils;

import java.io.Serializable;

/**
 * @author iamcyw
 **/
public class ListenerConfig extends AbstractServiceConfig<ListenerConfig> implements Serializable {

    private static final long serialVersionUID = -7490776773983374107L;

    /**
     * 实际监听协议类型
     */
    protected String protocol;

    /**
     * 启动器
     */
    protected String bootstrap;

    protected String serializerType;

    protected ListenerBootstrap listenerBootstrap;

    protected Transport transport;

    public String getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(String bootstrap) {
        this.bootstrap = bootstrap;
    }

    public Listener registry() {
        if (this.listenerBootstrap == null) {
            this.listenerBootstrap = Bootstraps.from(this);
        }
        return this.listenerBootstrap.registry();
    }

    public void unRegistry() {
        if (this.listenerBootstrap != null) {
            this.listenerBootstrap.unRegistry();
        }
    }

    public String getProtocol() {
        return StringUtils.isEmpty(protocol) ? CrossWayConfigs.getStringValue(CrossWayOptions.DEFAULT_LISTENER) :
               protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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
