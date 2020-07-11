package crossway.transport;

import crossway.config.ListenerConfig;
import crossway.config.SenderConfig;
import crossway.filter.FilterChainFactory;
import crossway.filter.FilterInvoker;
import crossway.filter.SenderInvoker;
import crossway.utils.CommonUtils;

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
    private       FilterInvoker  filterInvoker;
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
        listenerConfig.setTransport(this);
        senderConfig.setTransport(this);
    }

    public FilterInvoker getSend() {
        if (filterInvoker == null) {
            filterInvoker = FilterChainFactory.filterChain(new SenderInvoker(senderConfig), filters);
        }
        return filterInvoker;
    }
}