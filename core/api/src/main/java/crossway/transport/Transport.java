package crossway.transport;

import crossway.config.ListenerConfig;
import crossway.config.SenderConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.exception.CrossWayException;
import crossway.exception.WayErrorType;
import crossway.ext.ExtensionClass;
import crossway.ext.ExtensionLoaderFactory;
import crossway.filter.Filter;
import crossway.utils.CommonUtils;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * description:
 *
 * @author: Johnson Wang
 * @date: 2020/7/3 16:09
 * @copyright: 2020, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
public class Transport {
    private final ListenerConfig listenerConfig;
    private final SenderConfig   senderConfig;
    private       String[]       filters = new String[]{"log"};

    public Transport(ListenerConfig listenerConfig, SenderConfig senderConfig, String... filters) {
        this.listenerConfig = listenerConfig;
        this.senderConfig = senderConfig;
        if (CommonUtils.isNotEmpty(filters)) {
            this.filters = filters;
        }
        init();
    }

    private void init() {
        if (listenerConfig != null) {
            listenerConfig.setTransport(this);
        }
        if (senderConfig != null) {
            senderConfig.setTransport(this);
        }
    }

    public <T> CompletableFuture<CrossWayResponse> apply(Supplier<CrossWayRequest> supplier) {
        CompletableFuture<CrossWayRequest> future = CompletableFuture.supplyAsync(supplier);
        return apply(future);
    }

    public CompletableFuture<CrossWayResponse> apply(CompletableFuture<CrossWayRequest> future) {
        for (ExtensionClass<Filter> filterExtensionClass : getFilters()) {
            Filter filter = filterExtensionClass.getExtInstance();
            future = future.thenCompose(request -> CompletableFuture.supplyAsync(() -> {
                filter.request(request);
                return request;
            }));
        }
        CompletableFuture<CrossWayResponse> responseCompletableFuture = future.thenApplyAsync(request -> {
            CrossWayResponse response = new CrossWayResponse();
            try {
                response = senderConfig.refer().invoke(request);
            } catch (Throwable e) {
                response.setErrorMsg(e.getMessage());
                response.setError(new CrossWayException(WayErrorType.SERVER_SEND, e));
            }
            return response;
        });

        for (ExtensionClass<Filter> filterExtensionClass : getFilters()) {
            Filter filter = filterExtensionClass.getExtInstance();
            responseCompletableFuture = responseCompletableFuture.thenCompose(
                response -> CompletableFuture.supplyAsync(() -> {
                    filter.response(response);
                    return response;
                }));
        }
        return responseCompletableFuture;
    }

    private Collection<ExtensionClass<Filter>> getFilters() {
        return ExtensionLoaderFactory.getExtensionLoader(Filter.class).getAllExtensions().values();
    }
}