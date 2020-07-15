package crossway;

import crossway.core.request.CrossWayRequest;
import crossway.transport.Transport;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author iamcyw
 **/
@Slf4j
public class CrossWayTest {

    @Test
    public void testCrossWay() throws ExecutionException, InterruptedException {
        log.info("start");
        CrossWayRequest request = new CrossWayRequest();

        Transport transport = new Transport(null, null);

        transport.apply(CompletableFuture.supplyAsync(() -> request));


        log.info("end");
        Thread.sleep(1000);
    }
}
