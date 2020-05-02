package crossway.filter;

import crossway.api.Ordered;
import crossway.chain.IChain;
import crossway.context.GateWayContext;
import crossway.ext.api.Extension;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/3/1
 **/
@Slf4j
@Extension(value = "exceptionFilter", order = Ordered.HIGHEST_PRECEDENCE)
public class DefaultExceptionFilter implements ChainFilter {

    @Override
    public void apply(GateWayContext gateWayContext, IChain gateWayChain) {
        try {
            gateWayChain.apply(gateWayContext);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            gateWayContext.getMessageProvider().setResponseSource(e);
        }
    }
}
