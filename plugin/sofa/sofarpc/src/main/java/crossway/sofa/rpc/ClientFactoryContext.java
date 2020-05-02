package crossway.sofa.rpc;

import com.alipay.sofa.runtime.api.aware.ClientFactoryAware;

public class ClientFactoryContext implements ClientFactoryAware {

    private static com.alipay.sofa.runtime.api.client.ClientFactory clientFactory;

    public static <T> T getClient(Class<T> clazz) {
        return getClientFactory().getClient(clazz);
    }

    public static com.alipay.sofa.runtime.api.client.ClientFactory getClientFactory() {
        return clientFactory;
    }

    @Override
    public void setClientFactory(com.alipay.sofa.runtime.api.client.ClientFactory clientFactory) {
        ClientFactoryContext.clientFactory = clientFactory;
    }
}
