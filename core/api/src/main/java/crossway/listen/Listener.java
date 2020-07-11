package crossway.listen;

import crossway.config.ListenerConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.ext.api.Extensible;
import crossway.invoke.Invoker;
import crossway.utils.StringUtils;

import java.util.UUID;

/**
 * @author iamcyw
 **/
@Extensible
public abstract class Listener implements Invoker {

    protected final ListenerConfig config;
    private final   int            id = UUID.randomUUID().hashCode();

    public Listener(ListenerConfig config) {
        this.config = config;
    }

    @Override
    public CrossWayResponse invoke(CrossWayRequest request) {
        return getConfig().getTransport().getSend().invoke(request);
    }

    public ListenerConfig getConfig() {
        return config;
    }

    public int getId() {
        return id;
    }

    protected String getSeriallzerType() {
        return StringUtils.isNotEmpty(getConfig().getSerializerType()) ? getConfig().getSerializerType() :
               getDefaultSeriallzerType();
    }

    protected abstract String getDefaultSeriallzerType();

}
