package crossway.send;

import crossway.config.SenderConfig;
import crossway.ext.api.Extensible;
import crossway.invoke.Invoker;

import java.util.UUID;

/**
 * @author iamcyw
 **/
@Extensible
public abstract class Send implements Invoker {

    private final SenderConfig senderConfig;

    private final int id = UUID.randomUUID().hashCode();

    protected Send(SenderConfig senderConfig) {
        this.senderConfig = senderConfig;
    }

    public int getId() {
        return id;
    }
}
