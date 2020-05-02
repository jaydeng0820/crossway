package crossway.chain;

import crossway.context.GateWayContext;
import crossway.ext.ExtensionClass;
import crossway.ext.ExtensionLoaderFactory;
import crossway.filter.ChainFilter;
import crossway.send.SendServlet;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class GateWayQueueChain implements IChain {

    private final Queue<ChainFilter> chainFilters;
    private final SendServlet sendServlet;

    public GateWayQueueChain(SendServlet sendServlet) {
        this.sendServlet = sendServlet;
        chainFilters = init();
    }

    @Override
    public void apply(GateWayContext gateWayContext) {
        ChainFilter chainFilter = chainFilters.poll();
        if (chainFilter != null) {
            chainFilter.apply(gateWayContext, this);
        } else if (sendServlet != null) {
            sendServlet.apply(gateWayContext);
        }
    }

    @Override
    public SendServlet getSendServlet() {
        return sendServlet;
    }

    private Queue<ChainFilter> init() {
        return ExtensionLoaderFactory.getExtensionLoader(ChainFilter.class)
                .getAllExtensions()
                .values().stream()
                .sorted(Comparator.comparingInt(ExtensionClass::getOrder))
                .filter(ExtensionClass::isEnable)
                .map(ExtensionClass::getExtInstance)
                .collect(Collectors.toCollection(
                        LinkedList::new));
    }
}
