package crossway.impl.listen;

import crossway.codec.Serializer;
import crossway.codec.SerializerFactory;
import crossway.config.ListenerConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.ext.api.Extension;
import crossway.listen.Listener;

import java.util.concurrent.CompletableFuture;

/**
 * description:
 *
 * @author: Johnson Wang
 * @date: 2020/7/3 16:49
 * @copyright: 2020, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Extension("default-listener")
public class CrossWayListener extends Listener {
    public CrossWayListener(ListenerConfig config) {
        super(config);
    }

    public Object request(Object request) throws Exception {
        Serializer serializer = SerializerFactory.getSerializer(getSeriallzerType());

        CrossWayRequest crossWayRequest = new CrossWayRequest();
        crossWayRequest.setData(serializer.encode(request, null));

        CrossWayResponse response = getConfig().getTransport().getSend().invoke(crossWayRequest);

        if (response.isError()) {
            throw response.getError();
        }
        return serializer.decode(response.getData(), null);
    }

    public CompletableFuture<CrossWayResponse> requestAsync(Object request) throws Exception {
        CrossWayRequest crossWayRequest = new CrossWayRequest();
        return CompletableFuture.supplyAsync(() -> getConfig().getTransport().getSend().invoke(crossWayRequest));
    }

    @Override
    protected String getDefaultSeriallzerType() {
        return "string";
    }
}