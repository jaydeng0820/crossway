package crossway.send;

import crossway.exception.CrossWayRuntimeException;
import crossway.ext.ExtensionClass;
import crossway.ext.ExtensionLoaderFactory;
import crossway.log.LogCodes;

/**
 * @author iamcyw
 **/
public final class SendFactory {

    public static Send getSend(String sendType) {
        try {
            ExtensionClass<Send> ext = ExtensionLoaderFactory.getExtensionLoader(Send.class).getExtensionClass(
                sendType);
            if (ext == null) {
                throw new CrossWayRuntimeException(LogCodes.getLog(LogCodes.ERROR_LOAD_EXT, "Send", sendType));
            }
            return ext.getExtInstance();
        } catch (CrossWayRuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new CrossWayRuntimeException(LogCodes.getLog(LogCodes.ERROR_LOAD_EXT, "Send", sendType));
        }
    }
}
