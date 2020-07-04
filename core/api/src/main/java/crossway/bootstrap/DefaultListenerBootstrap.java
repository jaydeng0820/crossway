package crossway.bootstrap;

import crossway.config.ListenerConfig;
import crossway.ext.api.Extension;
import crossway.listen.Listener;
import crossway.listen.ListenerFactory;

/**
 * @author iamcyw
 **/
@Extension("default-listener")
public class DefaultListenerBootstrap extends ListenerBootstrap {

    protected Listener listener;

    protected DefaultListenerBootstrap(ListenerConfig listenerConfig) {
        super(listenerConfig);
    }

    @Override
    public Listener registry() {
        if (listener == null) {
            listener = ListenerFactory.getInvoker(getListenerConfig());
        }
        return listener;
    }

    @Override
    public void unRegistry() {

    }
}
