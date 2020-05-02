package crossway.pool;

import crossway.exception.CrossWayRuntimeException;
import crossway.model.AbstractCfMod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/28
 **/
@Slf4j
public abstract class AbstractSenderPool<C extends AbstractCfMod> extends
        BasePooledObjectFactory<ISender> implements
        ISenderPool<C> {

    private final String id;

    private ObjectPool<ISender> pool;

    public AbstractSenderPool(String id) {
        this.id = id;
    }

    public AbstractSenderPool(String id, AbstractCfMod config) {
        this.id = id;
        getConfig().putAll(config);
    }

    @Override
    public ISender borrowClient() {
        if (log.isInfoEnabled()) {
            log.info("borrowSender. SendPool ID + [ " + getId() + "]'s currentActive is [ " + getPool()
                    .getNumActive() + " ] and curreentIdel is [ " + getPool().getNumIdle() + "]");
        }

        try {
            return getPool().borrowObject();

        } catch (Exception e) {
            throw new CrossWayRuntimeException("send pool borrow error", e);
        }
    }

    @Override
    public void distroy() {
        try {
            getPool().clear();
        } catch (Exception e) {
            throw new CrossWayRuntimeException("send pool distroy error", e);
        }
    }

    public abstract C getConfig();


    public String getId() {
        return id;
    }

    public ObjectPool<ISender> getPool() {
        return pool;
    }

    @Override
    public void setPool(ObjectPool<ISender> pool) {
        this.pool = pool;
    }

    @Override
    public void returnClient(ISender sender) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("returnSender. SendPool ID + [ " + getId() + "]'s currentActive is [ " + getPool()
                    .getNumActive() + " ] and curreentIdel is [ " + getPool().getNumIdle() + "]");
        }
        getPool().returnObject(sender);
    }

    @Override
    public PooledObject<ISender> wrap(ISender sender) {
        return new DefaultPooledObject<ISender>(sender);
    }

}
