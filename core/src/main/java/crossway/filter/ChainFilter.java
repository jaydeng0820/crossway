package crossway.filter;

import crossway.chain.IChain;
import crossway.context.GateWayContext;
import crossway.ext.api.Extensible;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/27
 **/
@Extensible()
public interface ChainFilter {

    void apply(GateWayContext gateWayContext, IChain gateWayChain);
}
