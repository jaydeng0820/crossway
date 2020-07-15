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

    public abstract CrossWayRequest request(CrossWayRequest request);

    public abstract CrossWayResponse response(CrossWayResponse response);
}
