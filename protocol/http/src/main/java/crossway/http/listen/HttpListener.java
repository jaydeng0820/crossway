package crossway.http.listen;

import crossway.codec.Serializer;
import crossway.config.ListenerConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.listen.Listener;
import crossway.utils.StringUtils;
import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.post;

public class HttpListener extends Listener {
    public HttpListener(ListenerConfig config) {
        super(config);
        init();
    }

    private void init() {
        String action = getConfig().getParameter("action");
        if (StringUtils.equalsIgnoreCase(action, "post")) {
            post("", this::handle);
        } else if (StringUtils.equalsIgnoreCase(action, "get")) {
            get("", this::handle);
        }
    }

    private Object handle(Request request, Response response) {
        Serializer serializer = getSerializer();

        CrossWayRequest crossWayRequest = new CrossWayRequest();
        crossWayRequest.setData(serializer.encode(request.body(), null));

        CrossWayResponse crossWayResponse = invoke(crossWayRequest);
        if (crossWayResponse.isError()) {
            return crossWayResponse.getError();
        }
        return serializer.decode(crossWayResponse.getData(), null);
    }


    @Override
    protected String getDefaultSeriallzerType() {
        return "json";
    }
}
