package crossway;

import crossway.config.ListenerConfig;
import crossway.config.SenderConfig;
import crossway.impl.listen.CrossWayListener;
import crossway.impl.send.CrossWaySend;
import crossway.impl.send.CrossWaySendEvent;
import crossway.transport.Transport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author iamcyw
 **/
public class CrossWayTest {

    @Test
    public void testCrossWay() throws Exception {
        SenderConfig senderConfig = new SenderConfig();
        CrossWaySend invoker = (CrossWaySend) senderConfig.refer();

        ListenerConfig listenerConfig = new ListenerConfig();
        CrossWayListener listener = (CrossWayListener) listenerConfig.registry();

        Transport transport = new Transport(listenerConfig, senderConfig);

        invoker.setSendEvent(new CrossWaySendEvent() {
            @Override
            public Object event(Object message) {
                Assertions.assertEquals(message, "aaa");
                return "bbb";
            }
        });

        Assertions.assertEquals("bbb", listener.request("aaa"));

    }
}
