package crossway.filter;

import crossway.config.SenderConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.exception.CrossWayException;
import crossway.exception.WayErrorType;

/**
 * @author iamcyw
 **/
public class SenderInvoker extends FilterInvoker {

    protected final SenderConfig senderConfig;

    public SenderInvoker(SenderConfig config) {
        this.senderConfig = config;
    }

    @Override
    public CrossWayResponse invoke(CrossWayRequest request) throws CrossWayException {
        CrossWayResponse response = new CrossWayResponse();
        try {
            response = senderConfig.refer().invoke(request);
        } catch (Throwable e) {
            response.setErrorMsg(e.getMessage());
            response.setError(new CrossWayException(WayErrorType.SERVER_SEND, e));
        }
        return response;
    }

}
