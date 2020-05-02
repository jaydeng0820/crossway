package crossway.sofa.rpc.listener;

import crossway.model.AbstractCfMod;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/29
 **/
public class SofaRpcListenerConfig extends AbstractCfMod {

    private static final long serialVersionUID = -1177384524630910878L;

    private final String MILLISECONDSTODELAY = "millisecondsToDelay";
    private final String TIME_OUT = "timeout";

    public int getDelay() {
        return Integer.valueOf(this.get(this.MILLISECONDSTODELAY));
    }

    public long getTimeOut() {
        return Long.valueOf(this.get(this.TIME_OUT));
    }
}
