package crossway.sofa.rpc.listener;

import com.alipay.sofa.rpc.boot.runtime.param.BoltBindingParam;
import com.alipay.sofa.runtime.api.client.ServiceClient;
import com.alipay.sofa.runtime.api.client.param.BindingParam;
import com.alipay.sofa.runtime.api.client.param.ServiceParam;
import com.alipay.sofa.runtime.service.component.ServiceComponent;
import com.alipay.sofa.runtime.spi.component.ComponentInfo;
import com.alipay.sofa.runtime.spi.component.SofaRuntimeContext;
import com.alipay.sofa.runtime.spi.spring.SofaRuntimeContextAware;
import crossway.GateWayComplete;
import crossway.GateWayRuntimeContext;
import crossway.api.Destroyable;
import crossway.context.GateWayContext;
import crossway.ext.api.Extension;
import crossway.listener.AbstractListener;
import crossway.sofa.rpc.ClientFactoryContext;
import crossway.sofa.rpc.api.RpcMsg;
import crossway.sofa.rpc.api.RpcMsgCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/29
 **/
@Extension(value = "rpcListener")
public class SofaRpcListener extends AbstractListener<SofaRpcListenerConfig> implements
        RpcMsgCallback, SofaRuntimeContextAware {

    private SofaRuntimeContext sofaRuntimeContext;

    public SofaRpcListener(String id) {
        super(id);
    }

    @Override
    public void connect() {
        ServiceClient serviceClient = ClientFactoryContext.getClient(ServiceClient.class);

        ServiceParam serviceParam = new ServiceParam();
        serviceParam.setInterfaceType(RpcMsgCallback.class);
        serviceParam.setInstance(this);

        List<BindingParam> params = new ArrayList<>();
        BoltBindingParam serviceBindingParam = new BoltBindingParam();
        params.add(serviceBindingParam);
        serviceParam.setBindingParams(params);

        serviceClient.service(serviceParam);

        GateWayRuntimeContext.registryListenerDestroy(new Destroyable() {
            @Override
            public void destroy() {

            }

            @Override
            public void disconnect() {
                ClientFactoryContext.getClient(ServiceClient.class).removeService(RpcMsgCallback.class, 0);
            }
        });
    }

    @Override
    public SofaRpcListenerConfig getConfig() {
        return null;
    }

    @Override
    public boolean isconnect() {
        Collection<ComponentInfo> serviceComponents = sofaRuntimeContext.getComponentManager()
                .getComponentInfosByType(
                        ServiceComponent.SERVICE_COMPONENT_TYPE);
        for (ComponentInfo componentInfo : serviceComponents) {
            if (!(componentInfo instanceof ServiceComponent)) {
                continue;
            }

            ServiceComponent serviceComponent = (ServiceComponent) componentInfo;
            if (serviceComponent.getService().getInterfaceType() == RpcMsgCallback.class) {
                return true;
            }
        }
        return false;
    }

    @Override
    public RpcMsg send(RpcMsg rpcMsg) {
        GateWayContext context = new GateWayContext();

        context.setChainId(getSend());

        context.getMessageProvider().setRequestSource(rpcMsg);

        return GateWayComplete.complete(context).thenApply(gateWayContext -> {
            return gateWayContext.getMessageProvider().getResponse(RpcMsg.class).getTarget();
        }).join();
    }

    @Override
    public void setSofaRuntimeContext(SofaRuntimeContext sofaRuntimeContext) {
        this.sofaRuntimeContext = sofaRuntimeContext;
    }
}
