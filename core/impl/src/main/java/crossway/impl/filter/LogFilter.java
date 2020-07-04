package crossway.impl.filter;

import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.ext.api.Extension;
import crossway.filter.Filter;
import crossway.filter.FilterInvoker;

/**
 * description:
 *
 * @author: Johnson Wang
 * @date: 2020/7/3 17:14
 * @copyright: 2020, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Extension("log")
public class LogFilter extends Filter {
    @Override
    public CrossWayResponse invoke(FilterInvoker invoker, CrossWayRequest request) {
        return invoker.invoke(request);
    }
}