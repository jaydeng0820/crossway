package crossway.config;

import crossway.filter.Filter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static crossway.common.CrossWayConfigs.getStringValue;
import static crossway.common.CrossWayOptions.DEFAULT_UNIQUEID;

/**
 * 服务级的公共配置
 *
 * @author iamcyw
 **/
@Slf4j
public class AbstractServiceConfig<S extends AbstractServiceConfig> extends AbstractIdConfig<S> implements Serializable {

    private static final long serialVersionUID = 4655838340050434773L;

    /**
     * 过滤器配置实例
     */
    protected transient List<Filter> filterRef;

    /**
     * 过滤器配置别名，多个用逗号隔开
     */
    protected List<String>              filter;
    protected Map<String, MethodConfig> methods;

    /**
     * 自定义参数
     */
    protected Map<String, String> parameters;
    private   String              serviceId;
    private   String              uniqueId = getStringValue(DEFAULT_UNIQUEID);

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Sets parameters.
     *
     * @param parameters
     *     the parameters
     *
     * @return the parameters
     */
    public S setParameters(Map<String, String> parameters) {
        if (this.parameters == null) {
            this.parameters = new ConcurrentHashMap<String, String>();
        }
        this.parameters.putAll(parameters);
        return castThis();
    }

    /**
     * Sets parameter.
     *
     * @param key
     *     the key
     * @param value
     *     the value
     *
     * @return the parameter
     */
    public S setParameter(String key, String value) {
        if (parameters == null) {
            parameters = new ConcurrentHashMap<String, String>();
        }
        if (value == null) {
            parameters.remove(key);
        } else {
            parameters.put(key, value);
        }
        return castThis();
    }

    /**
     * Gets parameter.
     *
     * @param key
     *     the key
     *
     * @return the value
     */
    public String getParameter(String key) {
        return parameters == null ? null : parameters.get(key);
    }


}
