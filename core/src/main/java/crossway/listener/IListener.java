package crossway.listener;

import crossway.ext.api.Extensible;
import crossway.model.AbstractCfMod;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/28
 **/
@Extensible()
public interface IListener<C extends AbstractCfMod> {

    void connect();

    boolean isconnect();
}
