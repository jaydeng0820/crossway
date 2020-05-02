package crossway.send;

import crossway.context.GateWayContext;
import crossway.ext.api.Extensible;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/3/2
 **/
@Extensible
public interface SendServlet {

    void apply(GateWayContext gateWayContext);

    String sendType();

}
