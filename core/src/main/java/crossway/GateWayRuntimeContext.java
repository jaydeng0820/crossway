package crossway;

import crossway.api.Destroyable;
import crossway.listener.IListener;
import crossway.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/3/9
 **/
@Slf4j
public class GateWayRuntimeContext {

    /**
     * 关闭资源的钩子
     */
    private final static List<Destroyable> DESTROY_LISTENER = new CopyOnWriteArrayList<>();
    private final static List<Destroyable> DESTROY_SENDER = new CopyOnWriteArrayList<>();

    static {

        Thread shutdownThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (log.isWarnEnabled()) {
                    log.warn("SESA GATEWAY Framework catch JVM shutdown event, Run shutdown hook now.");
                }
                destroy();
            }
        }, "SESA-GATEWAY-ShutdownHook");

        IdentityHashMap<Thread, Thread> excludeIdentityHashMap = new ExcludeIdentityHashMap<>();
        excludeIdentityHashMap.put(shutdownThread, shutdownThread);

        try {
            String className = "java.lang.ApplicationShutdownHooks";
            Class<?> clazz = ClassUtils.forName(className);
            Field field = clazz.getDeclaredField("hooks");
            field.setAccessible(true);

            synchronized (clazz) {
                IdentityHashMap<Thread, Thread> map = (IdentityHashMap<Thread, Thread>) field.get(clazz);
                for (Thread thread : map.keySet()) {
                    excludeIdentityHashMap.put(thread, thread);
                }

                field.set(clazz, excludeIdentityHashMap);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final List<IListener> listeners;
    private final Executor executor;

    public GateWayRuntimeContext(List<IListener> listeners, Executor executor) {
        this.listeners = listeners;
        this.executor = executor;
    }

    private static void destroy() {
        // 1. 监听断连
        DESTROY_LISTENER.forEach(Destroyable::disconnect);

        //2. 线程池销毁
        GateWayComplete.destroy();

        //3. 监听销毁
        DESTROY_LISTENER.forEach(Destroyable::destroy);

        //4 销毁发送
        DESTROY_SENDER.forEach(Destroyable::destroy);

    }

    /**
     * 注册销毁器
     *
     * @param destroyable 销毁器
     */
    public static void registryListenerDestroy(Destroyable destroyable) {
        DESTROY_LISTENER.add(destroyable);
    }

    /**
     * 注册销毁器
     *
     * @param destroyable 销毁器
     */
    public static void registrySenderDestroy(Destroyable destroyable) {
        DESTROY_SENDER.add(destroyable);
    }

}

class ExcludeIdentityHashMap<K, V> extends IdentityHashMap<K, V> {

    private static final long serialVersionUID = -4844524091978519152L;

    @Override
    public V put(K key, V value) {
        if (key instanceof Thread) {
            Thread thread = (Thread) key;
            if (thread.getName().startsWith("SOFA-")) {
                return value;
            }
        }
        return super.put(key, value);
    }
}
