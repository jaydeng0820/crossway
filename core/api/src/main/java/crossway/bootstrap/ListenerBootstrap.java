package crossway.bootstrap;

import crossway.config.ListenerConfig;
import crossway.ext.api.Extensible;
import crossway.listen.Listener;

/**
 * @author iamcyw
 **/
@Extensible(singleton = false)
public abstract class ListenerBootstrap {

    protected final ListenerConfig listenerConfig;

    protected ListenerBootstrap(ListenerConfig listenerConfig) {
        this.listenerConfig = listenerConfig;
    }

    public ListenerConfig getListenerConfig() {
        return listenerConfig;
    }

    public abstract Listener registry();

    public abstract void unRegistry();
}
