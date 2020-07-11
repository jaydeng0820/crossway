package crossway;

import crossway.config.ListenerConfig;
import crossway.config.SenderConfig;
import crossway.filter.FilterInvoker;
import crossway.send.Send;
import crossway.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author iamcyw
 **/
@Slf4j
public class CrossWayTest {

    @Test
    public void testCrossWay() {

        int i = 0;

        System.out.println(i++);

        System.out.println(++i);

        i--;
        System.out.println(i);


//        SenderConfig senderConfig = new SenderConfig();
//        Send invoker =  senderConfig.refer();
//
//        ListenerConfig listenerConfig = new ListenerConfig();
//        listenerConfig.registry();
    }
}
