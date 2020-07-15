package crossway.config;

import crossway.context.CrossWayRuntimeContext;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author iamcyw
 **/
public class AbstractIdConfig<S extends AbstractIdConfig> implements Serializable {

    private static final long serialVersionUID = -1932911135229369183L;

    /**
     * Id生成器
     */
    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    static {
        CrossWayRuntimeContext.now();
    }

    /**
     * config id
     */
    private String id;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        if (id == null) {
            synchronized (this) {
                if (id == null) {
                    id = "way-cfg-" + ID_GENERATOR.getAndIncrement();
                }
            }
        }
        return id;
    }

    /**
     * Sets id.
     *
     * @param id
     *     the id
     */
    public S setId(String id) {
        this.id = id;
        return castThis();
    }

    protected S castThis() {
        return (S) this;
    }
}
