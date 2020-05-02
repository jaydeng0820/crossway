package crossway.chain;

import crossway.context.GateWayContext;
import crossway.send.SendServlet;

public interface IChain {

    void apply(GateWayContext gateWayContext);

    SendServlet getSendServlet();
}
