package crossway.filter;

import crossway.config.AbstractServiceConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.exception.CrossWayException;
import crossway.exception.WayErrorType;
import crossway.invoke.Invoker;
import crossway.log.LogCodes;

import javax.annotation.concurrent.ThreadSafe;

/**
 * @author iamcyw
 **/
@ThreadSafe
public class FilterInvoker implements Invoker {

    /**
     * 下一层过滤器
     */
    protected Filter nextFilter;

    /**
     * 下一层Invoker
     */
    protected FilterInvoker invoker;

    public FilterInvoker() {
    }

    public FilterInvoker(Filter nextFilter, FilterInvoker invoker) {
        this.nextFilter = nextFilter;
        this.invoker = invoker;
    }

    @Override
    public CrossWayResponse invoke(CrossWayRequest request) throws CrossWayException {
        if (nextFilter == null && invoker == null) {
            throw new CrossWayException(WayErrorType.SERVER_FILTER,
                                        LogCodes.getLog(LogCodes.ERROR_NEXT_FILTER_AND_INVOKER_NULL));
        }
        return nextFilter == null ? invoker.invoke(request) : nextFilter.invoke(invoker, request);
    }

}
