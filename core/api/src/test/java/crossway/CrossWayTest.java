package crossway;

import crossway.config.ListenerConfig;
import crossway.config.SenderConfig;
import crossway.filter.FilterInvoker;
import crossway.send.Send;
import org.junit.jupiter.api.Test;

/**
 * @author iamcyw
 **/
public class CrossWayTest {

    @Test
    public void testCrossWay() {
        SenderConfig senderConfig = new SenderConfig();
        Send invoker =  senderConfig.refer();

        ListenerConfig listenerConfig = new ListenerConfig();
        listenerConfig.registry();
    }
}
