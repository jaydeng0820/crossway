package crossway.http.send;

import crossway.config.SenderConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.exception.CrossWayException;
import crossway.ext.api.Extension;
import crossway.send.Send;

@Extension("http")
public class HttpSender extends Send {
    protected HttpSender(SenderConfig senderConfig) {
        super(senderConfig);
    }

    @Override
    protected String getDefaultSerializeType() {
        return "json";
    }

    @Override
    public CrossWayResponse invoke(CrossWayRequest request) throws CrossWayException {
        String message = (String) getSerializer().decode(request.getData(), null);
        getConfig().getParameter("");
        return null;
    }
}
