package crossway.pool;

import crossway.model.AbstractCfMod;
import org.apache.commons.pool2.ObjectPool;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/28
 **/
public interface ISenderPool<C extends AbstractCfMod> {

    ISender borrowClient();

    void distroy();

    void returnClient(ISender sender) throws Exception;

    void setPool(ObjectPool<ISender> pool);

}
