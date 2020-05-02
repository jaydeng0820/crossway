package crossway.sofa.rpc.sender;

import com.alipay.hessian.generic.model.GenericObject;
import com.alipay.sofa.rpc.api.GenericService;
import com.alipay.sofa.rpc.context.RpcRuntimeContext;
import crossway.GateWayRuntimeContext;
import crossway.api.Destroyable;
import crossway.bonding.Bonding;
import crossway.bonding.Bondings;
import crossway.context.GateWayContext;
import crossway.ext.api.Extension;
import crossway.message.converter.MessageConverter;
import crossway.send.SendServlet;
import crossway.send.SendType;
import crossway.spring.SpringContext;
import crossway.utils.BeanUtils;
import crossway.utils.ClassUtils;

import java.util.Map;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/29
 **/
@Extension(value = "rpc")
public class SofaRpcSend implements SendServlet {

    private final String DEFAULT_BONDING = "rpcMethod";

    public SofaRpcSend() {
        GateWayRuntimeContext.registrySenderDestroy(new Destroyable() {
            @Override
            public void destroy() {
                RpcRuntimeContext.destroy();
            }

            @Override
            public void disconnect() {
            }
        });
    }

    @Override
    public void apply(GateWayContext gateWayContext) {

        MessageConverter<?, SofaRequest> rpcRequest = gateWayContext.getMessageProvider().getRequest(
                SofaRequest.class);

        Bondings bondings = SpringContext.getBean("rpcMethod");
        Bonding bonding = bondings.get(gateWayContext.getMessageProvider().getMetaData().getMethod());

        if (bonding == null) {
            throw new NullPointerException("message rpc method");
        }

        GenericService genericServiceReference = SpringContext.getBean(bonding.get("invokeBean"));

        Object arg = null;
        if (rpcRequest.getTarget().getBody() instanceof Map) {
            arg = ClassUtils.newInstance(ClassUtils.forName(bonding.get("invokeMethodArg")));
            BeanUtils.copyMapToBean(arg, (Map) rpcRequest.getTarget().getBody());
        }

        if (arg == null) {
            arg = rpcRequest.getTarget().getBody();
        }

        GenericObject genericResult = (GenericObject) genericServiceReference.$genericInvoke(
                bonding.get("invokeMethod").toString(),
                new String[]{bonding.get("invokeMethodArg")},
                new Object[]{arg});

        gateWayContext.getMessageProvider().setResponseSource(genericResult);

    }

    @Override
    public String sendType() {
        return SendType.rpc.toString();
    }

}
