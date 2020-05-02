package crossway.pool;

import java.util.Map;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/28
 **/
public interface ISender {

    void send(Object object);

    Object sendReply(Object object);

    Object sendReply(Object object, Map<String, Object> objectProperties);
}
