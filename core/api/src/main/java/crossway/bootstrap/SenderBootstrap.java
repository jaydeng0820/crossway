package crossway.bootstrap;

import crossway.config.SenderConfig;
import crossway.ext.api.Extensible;
import crossway.filter.FilterInvoker;
import crossway.send.Send;

/**
 * @author iamcyw
 **/
@Extensible(singleton = false)
public abstract class SenderBootstrap {

    protected final SenderConfig senderConfig;

    public SenderBootstrap(SenderConfig senderConfig) {
        this.senderConfig = senderConfig;
    }

    public SenderConfig getSenderConfig() {
        return senderConfig;
    }

    public abstract Send refer();

    public abstract void unRefer();


}
