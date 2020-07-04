package crossway.filter;

import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.ext.api.Extensible;

/**
 * Filter SPI
 *
 * @author iamcyw
 **/
@Extensible(singleton = true)
public abstract class Filter {

    public abstract CrossWayResponse invoke(FilterInvoker invoker, CrossWayRequest request);

    public boolean needToLoad(FilterInvoker invoker) {
        return true;
    }


}
