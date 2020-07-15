package crossway.send;

import crossway.config.SenderConfig;
import crossway.exception.CrossWayRuntimeException;
import crossway.ext.ExtensionClass;
import crossway.ext.ExtensionLoaderFactory;
import crossway.log.LogCodes;

/**
 * @author iamcyw
 **/
public final class SendFactory {

    public static Send getSend(SenderConfig senderConfig) {
        try {
            ExtensionClass<Send> ext = ExtensionLoaderFactory.getExtensionLoader(Send.class).getExtensionClass(
                senderConfig.getProtocol());
            if (ext == null) {
                throw new CrossWayRuntimeException(
                    LogCodes.getLog(LogCodes.ERROR_LOAD_EXT, "Send", senderConfig.getProtocol()));
            }
            return ext.getExtInstance(new Class[]{SenderConfig.class}, new Object[]{senderConfig});
        } catch (CrossWayRuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new CrossWayRuntimeException(
                LogCodes.getLog(LogCodes.ERROR_LOAD_EXT, "Send", senderConfig.getProtocol()));
        }
    }
}
