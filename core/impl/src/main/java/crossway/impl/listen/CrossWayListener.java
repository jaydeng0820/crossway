package crossway.impl.listen;

import crossway.codec.Serializer;
import crossway.config.ListenerConfig;
import crossway.core.request.CrossWayRequest;
import crossway.ext.api.Extension;
import crossway.listen.Listener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * description:
 *
 * @author: Johnson Wang
 * @date: 2020/7/3 16:49
 * @copyright: 2020, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Slf4j
@Extension("default-listener")
public class CrossWayListener extends Listener {
    public CrossWayListener(ListenerConfig config) {
        super(config);
    }

    public Object request(Object request) {
        Serializer serializer = getSerializer();

        CrossWayRequest crossWayRequest = new CrossWayRequest();
        crossWayRequest.setData(serializer.encode(request, null));

        return getConfig().getTransport().apply(CompletableFuture.supplyAsync(() -> crossWayRequest)).thenApplyAsync(
            response -> {
                if (response.isError()) {
                    throw (RuntimeException) response.getError();
                }

                return serializer.decode(response.getData(), null);

            }).join();
    }

    public CompletableFuture<Object> async(Object request) {
        return getConfig().getTransport().apply(() -> {
            Serializer serializer = getSerializer();

            CrossWayRequest crossWayRequest = new CrossWayRequest();
            crossWayRequest.setData(serializer.encode(request, null));
            return crossWayRequest;
        }).thenApplyAsync(response -> {
            if (response.isError()) {
                throw (RuntimeException) response.getError();
            }

            return getSerializer().decode(response.getData(), null);
        });
    }

    @Override
    protected String getDefaultSerializeType() {
        return "string";
    }
}