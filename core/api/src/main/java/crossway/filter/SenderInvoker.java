package crossway.filter;

import crossway.config.SenderConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.exception.CrossWayException;
import crossway.exception.CrossWayRuntimeException;
import crossway.exception.WayErrorType;
import crossway.send.SendFactory;

/**
 * @author iamcyw
 **/
public class SenderInvoker extends FilterInvoker {

    public SenderInvoker(SenderConfig config) {
        super(config);
    }

    @Override
    public CrossWayResponse invoke(CrossWayRequest request) throws CrossWayException {
        CrossWayResponse response = new CrossWayResponse();
        try {
            response = SendFactory.getSend(getSenderConfig().getProtocol()).invoke(request);
        } catch (CrossWayRuntimeException e) {
            response.setAppResponse(e);
            response.setErrorMsg(e.getMessage());
        } catch (Throwable e) {
            response.setAppResponse(e);
            response.setAppResponse(new CrossWayException(WayErrorType.SERVER_SEND, e));
        }
        return response;
    }

    public SenderConfig getSenderConfig() {
        return (SenderConfig) this.config;
    }
}
