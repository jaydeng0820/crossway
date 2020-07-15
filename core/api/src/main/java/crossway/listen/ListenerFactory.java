package crossway.listen;

import crossway.config.ListenerConfig;
import crossway.exception.CrossWayRuntimeException;
import crossway.ext.ExtensionClass;
import crossway.ext.ExtensionLoaderFactory;
import crossway.log.LogCodes;

/**
 * Factory of Proxy SPI
 *
 * @author iamcyw
 **/
public final class ListenerFactory {

    public static Listener getInvoker(ListenerConfig config) {
        return getInvoker(config, config.getProtocol());
    }

    /**
     * @param config
     * @param listenType
     *
     * @return
     */
    public static Listener getInvoker(ListenerConfig config, String listenType) {
        try {
            ExtensionClass<Listener> ext = ExtensionLoaderFactory.getExtensionLoader(Listener.class).getExtensionClass(
                listenType);
            if (ext == null) {
                throw new CrossWayRuntimeException(LogCodes.getLog(LogCodes.ERROR_LOAD_EXT, "Listener", listenType));
            }
            return ext.getExtInstance(new Class[]{ListenerConfig.class}, new Object[]{config});
        } catch (CrossWayRuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new CrossWayRuntimeException(LogCodes.getLog(LogCodes.ERROR_LOAD_EXT, "Listener", listenType));
        }
    }

}
