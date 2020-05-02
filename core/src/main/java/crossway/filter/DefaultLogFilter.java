package crossway.filter;

import crossway.api.Ordered;
import crossway.chain.IChain;
import crossway.context.GateWayContext;
import crossway.ext.api.Extension;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/3/4
 **/
@Extension(value = "defaultLog", order = Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
public class DefaultLogFilter implements ChainFilter {

    @Override
    public void apply(GateWayContext gateWayContext, IChain gateWayChain) {
        log.info(gateWayContext.getMessageProvider().getRequestSource().toString());
        gateWayChain.apply(gateWayContext);
        if (gateWayContext.getMessageProvider().getResponseSource() != null) {
            log.info(gateWayContext.getMessageProvider().getResponseSource().toString());
        }
    }
}
