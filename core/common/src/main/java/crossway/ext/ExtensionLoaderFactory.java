package crossway.ext;

import crossway.ext.api.ExtensionLoaderListener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Factory of ExtensionLoader
 */
public class ExtensionLoaderFactory {
    /**
     * All extension loader {Class : ExtensionLoader}
     */
    private static final ConcurrentMap<Class, ExtensionLoader> LOADER_MAP = new ConcurrentHashMap<Class,
        ExtensionLoader>();

    private ExtensionLoaderFactory() {
    }

    /**
     * Get extension loader by extensible class with listener
     *
     * @param clazz
     *     Extensible class
     * @param listener
     *     Listener of ExtensionLoader
     * @param <T>
     *     Class
     *
     * @return ExtensionLoader of this class
     */
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> clazz, ExtensionLoaderListener<T> listener) {
        ExtensionLoader<T> loader = LOADER_MAP.get(clazz);
        if (loader == null) {
            synchronized (ExtensionLoaderFactory.class) {
                loader = LOADER_MAP.get(clazz);
                if (loader == null) {
                    loader = new ExtensionLoader<T>(clazz, listener);
                    LOADER_MAP.put(clazz, loader);
                }
            }
        }
        return loader;
    }

    /**
     * Get extension loader by extensible class without listener
     *
     * @param clazz
     *     Extensible class
     * @param <T>
     *     Class
     *
     * @return ExtensionLoader of this class
     */
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> clazz) {
        return getExtensionLoader(clazz, null);
    }
}
