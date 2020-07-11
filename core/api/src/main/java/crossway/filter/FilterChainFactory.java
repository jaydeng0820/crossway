package crossway.filter;

import crossway.exception.CrossWayRuntimeException;
import crossway.ext.ExtensionClass;
import crossway.ext.ExtensionLoaderFactory;
import crossway.log.LogCodes;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author iamcyw
 **/
@Slf4j
public class FilterChainFactory {

    private final static Map<String, ExtensionClass<Filter>> ALL_FILTER = ExtensionLoaderFactory.getExtensionLoader(
        Filter.class).getAllExtensions();

    /**
     * 构造执行链
     *
     * @param loadFilters
     *     包装过滤器列表
     * @param lastInvoker
     *     最终过滤器
     */
    public static FilterInvoker filterChain(FilterInvoker lastInvoker, String... loadFilters) {
        FilterInvoker invokerChain = lastInvoker;

        for (int i = loadFilters.length - 1; i >= 0; i--) {
            try {
                String filterId = loadFilters[i];
                Filter filter = ALL_FILTER.get(filterId).getExtInstance();
                if (filter.needToLoad(invokerChain)) {
                    invokerChain = new FilterInvoker(filter, invokerChain);
                }
            } catch (CrossWayRuntimeException e) {
                log.error(LogCodes.getLog(LogCodes.ERROR_FILTER_CONSTRUCT), e);
                throw e;
            } catch (Exception e) {
                log.error(LogCodes.getLog(LogCodes.ERROR_FILTER_CONSTRUCT), e);
                throw new CrossWayRuntimeException(LogCodes.getLog(LogCodes.ERROR_FILTER_CONSTRUCT), e);
            }
        }
        return invokerChain;
    }

}
