package crossway;

import crossway.chain.GateWayQueueChain;
import crossway.context.GateWayContext;
import crossway.spring.SpringContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/28
 **/
public class GateWayComplete {

    private static ThreadPoolExecutor EXECUTOR = null;

    public static CompletableFuture<GateWayContext> complete(GateWayContext gateWayContext) {
        CompletableFuture<GateWayContext> completableFuture = CompletableFuture.completedFuture(
                gateWayContext).thenApplyAsync(context -> {
            new GateWayQueueChain(SpringContext.getBean(gateWayContext.getChainId())).apply(context);
            return context;
        }, EXECUTOR);

        return completableFuture;
    }

    public static void destroy() {
        try {
            EXECUTOR.awaitTermination(111, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
        }
        EXECUTOR.shutdown();
    }

}
