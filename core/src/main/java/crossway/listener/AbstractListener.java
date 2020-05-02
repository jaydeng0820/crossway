package crossway.listener;

import crossway.model.AbstractCfMod;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/3/1
 **/
public abstract class AbstractListener<C extends AbstractCfMod> implements IListener<C> {

    public String id;
    public String send;

    protected AbstractListener(String id) {
        this.id = id;
    }

    protected AbstractListener(String id, String send) {
        this.id = id;
        this.send = send;
    }

    protected AbstractListener(String id, String send, AbstractCfMod cfMod) {
        this.id = id;
        this.send = send;
        if (cfMod != null) {
            getConfig().putAll(cfMod);
        }
    }

    public abstract C getConfig();

    public String getSend() {
        return this.send;
    }

    public void setSend(String send) {
        this.send = send;
    }
}
