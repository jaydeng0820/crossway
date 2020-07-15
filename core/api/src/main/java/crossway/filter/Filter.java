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
public interface Filter {

    public abstract void request(CrossWayRequest request);

    public abstract void response(CrossWayResponse response);
}
