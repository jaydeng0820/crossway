package crossway.sofa.rpc.api;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/29
 **/
public interface RpcMsgCallback {

    Object send(RpcMsg rpcMsg);

}
