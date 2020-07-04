package crossway;

import crossway.config.ListenerConfig;
import crossway.config.SenderConfig;
import crossway.impl.listen.CrossWayListener;
import crossway.send.Send;
import crossway.transport.Transport;
import org.junit.jupiter.api.Test;

/**
 * @author iamcyw
 **/
public class CrossWayTest {

    @Test
    public void testCrossWay() throws Exception {
        SenderConfig senderConfig = new SenderConfig();
        Send invoker = senderConfig.refer();

        ListenerConfig listenerConfig = new ListenerConfig();
        CrossWayListener listener = (CrossWayListener) listenerConfig.registry();

        Transport transport = new Transport(listenerConfig, senderConfig);

        listener.request("aaa");
    }
}
