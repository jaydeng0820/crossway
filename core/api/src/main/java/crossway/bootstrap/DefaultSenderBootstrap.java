package crossway.bootstrap;

import crossway.api.Destroyable;
import crossway.config.SenderConfig;
import crossway.context.CrossWayRuntimeContext;
import crossway.exception.CrossWayRuntimeException;
import crossway.ext.api.Extension;
import crossway.log.LogCodes;
import crossway.send.Send;
import crossway.send.SendFactory;
import crossway.utils.StringUtils;

/**
 * @author iamcyw
 **/
@Extension("default_bootstrap_send")
public class DefaultSenderBootstrap extends SenderBootstrap {

    private Send send;

    public DefaultSenderBootstrap(SenderConfig senderConfig) {
        super(senderConfig);
    }

    @Override
    public Send refer() {
        if (send == null) {
            send = SendFactory.getSend(getSenderConfig());
        }

        return send;
    }

    @Override
    public void unRefer() {
        if (send == null) {

        }
        Destroyable.DestroyHook hook = CrossWayRuntimeContext.getDestroyHook(StringUtils.toString(send.getId()));
        if (hook == null) {
            throw new CrossWayRuntimeException(
                LogCodes.getLog(LogCodes.ERROR_LOAD_HOOK_NULL, getSenderConfig().getId()));
        }
        hook.preDestroy();
        hook.postDestroy();
    }
}
