package crossway.impl.filter;

import crossway.codec.SerializerFactory;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.ext.api.Extension;
import crossway.filter.Filter;
import lombok.extern.slf4j.Slf4j;

/**
 * description:
 *
 * @author: Johnson Wang
 * @date: 2020/7/3 17:14
 * @copyright: 2020, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Slf4j
@Extension("log")
public class LogFilter implements Filter {

    @Override
    public CrossWayRequest request(CrossWayRequest request) {
        log.info(SerializerFactory.getSerializer("string").decode(request.getData(), null).toString());
        return request;
    }

    @Override
    public CrossWayResponse response(CrossWayResponse response) {
        return response;
    }
}