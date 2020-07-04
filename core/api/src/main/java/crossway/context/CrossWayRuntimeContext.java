package crossway.context;

import crossway.api.Destroyable;
import crossway.common.CrossWayConfigs;
import crossway.common.CrossWayOptions;
import crossway.module.ModuleFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局的运行时上下文
 *
 * @author iamcyw
 **/
@Slf4j
public class CrossWayRuntimeContext {

    /**
     * 关闭资源的钩子
     */
    private final static Map<String, Destroyable.DestroyHook> DESTROY = new ConcurrentHashMap<>();

    static {
        // 初始化其它模块
        ModuleFactory.installModules();
        // 增加jvm关闭事件
        if (CrossWayConfigs.getOrDefaultValue(CrossWayOptions.JVM_SHUTDOWN_HOOK, true)) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (log.isWarnEnabled()) {
                    log.warn("CrossWay Framework catch JVM shutdown event, Run shutdown hook now.");
                }
                destroy();
            }, "CROSS-WAY-ShutdownHook"));
        }
    }

    public static Destroyable.DestroyHook getDestroyHook(String serviceId) {
        return DESTROY.get(serviceId);
    }

    private static void destroy() {
        DESTROY.values().forEach(Destroyable.DestroyHook::preDestroy);

        DESTROY.values().forEach(Destroyable.DestroyHook::postDestroy);
    }

    /**
     * 获取当前时间，此处可以做优化
     *
     * @return 当前时间
     */
    public static long now() {
        return System.currentTimeMillis();
    }
}
