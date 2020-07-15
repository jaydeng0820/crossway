package crossway.http.listen;

import crossway.codec.Serializer;
import crossway.config.ListenerConfig;
import crossway.core.request.CrossWayRequest;
import crossway.ext.api.Extension;
import crossway.listen.Listener;
import crossway.utils.StringUtils;
import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.post;

@Extension("http")
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
        return getConfig().getTransport().apply(() -> {
            Serializer serializer = getSerializer();

            CrossWayRequest crossWayRequest = new CrossWayRequest();
            crossWayRequest.setData(serializer.encode(request.body(), null));
            return crossWayRequest;
        }).thenApplyAsync(crossWayResponse -> {
            if (crossWayResponse.isError()) {
                return crossWayResponse.getError();
            }
            return getSerializer().decode(crossWayResponse.getData(), null);
        }).join();
    }


    @Override
    protected String getDefaultSerializeType() {
        return "json";
    }
}
