package crossway.filter;

import crossway.config.AbstractServiceConfig;
import crossway.core.request.CrossWayRequest;
import crossway.core.response.CrossWayResponse;
import crossway.exception.CrossWayException;
import crossway.exception.CrossWayRuntimeException;
import crossway.ext.ExtensionClass;
import crossway.ext.ExtensionLoader;
import crossway.ext.ExtensionLoaderFactory;
import crossway.invoke.Invoker;
import crossway.log.LogCodes;
import crossway.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author iamcyw
 **/
@Slf4j
public class FilterChain implements Invoker {

    private final static Map<String, ExtensionClass<Filter>> AUTO_ACTIVES = Collections.synchronizedMap(
        new LinkedHashMap<String, ExtensionClass<Filter>>());

    /**
     * 扩展加载器
     */
    private final static ExtensionLoader<Filter> EXTENSION_LOADER = buildLoader();

    /**
     * 调用链
     */
    private FilterInvoker invokerChain;

    /**
     * 过滤器列表，从底至上排序
     */
    private List<Filter> loadedFilters;

    /**
     * 构造执行链
     *
     * @param filters
     *     包装过滤器列表
     * @param lastInvoker
     *     最终过滤器
     * @param config
     *     接口配置
     */
    public FilterChain(List<Filter> filters, FilterInvoker lastInvoker, AbstractServiceConfig config) {
        invokerChain = lastInvoker;
        if (CommonUtils.isNotEmpty(filters)) {
            loadedFilters = new ArrayList<>();
            for (int i = filters.size() - 1; i >= 0; i--) {
                try {
                    Filter filter = filters.get(i);
                    if (filter.needToLoad(invokerChain)) {
                        invokerChain = new FilterInvoker(filter, invokerChain, config);
                        loadedFilters.add(filter);
                    }
                } catch (CrossWayRuntimeException e) {
                    log.error(LogCodes.getLog(LogCodes.ERROR_FILTER_CONSTRUCT), e);
                    throw e;
                } catch (Exception e) {
                    log.error(LogCodes.getLog(LogCodes.ERROR_FILTER_CONSTRUCT), e);
                    throw new CrossWayRuntimeException(LogCodes.getLog(LogCodes.ERROR_FILTER_CONSTRUCT), e);
                }
            }
        }
    }

    private static ExtensionLoader<Filter> buildLoader() {
        return ExtensionLoaderFactory.getExtensionLoader(Filter.class,
                                                         extensionClass -> AUTO_ACTIVES.put(extensionClass.getAlias(),
                                                                                            extensionClass));
    }

    @Override
    public CrossWayResponse invoke(CrossWayRequest request) throws CrossWayException {
        return invokerChain.invoke(request);
    }
}
