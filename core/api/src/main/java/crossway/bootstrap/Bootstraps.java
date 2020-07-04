package crossway.bootstrap;

import crossway.common.CrossWayConfigs;
import crossway.common.CrossWayOptions;
import crossway.config.ListenerConfig;
import crossway.config.SenderConfig;
import crossway.ext.ExtensionLoaderFactory;
import crossway.utils.StringUtils;

/**
 * @author iamcyw
 **/
public class Bootstraps {

    public static SenderBootstrap from(SenderConfig senderConfig) {
        String bootstrap = senderConfig.getBootstrap();
        if (StringUtils.isEmpty(bootstrap)) {
            //使用默认监听器
            bootstrap = CrossWayConfigs.getStringValue(CrossWayOptions.DEFAULT_BOOTSTRAP_SEND);
            senderConfig.setBootstrap(bootstrap);
        }
        return ExtensionLoaderFactory.getExtensionLoader(SenderBootstrap.class).getExtension(
            senderConfig.getBootstrap(), new Class[]{SenderConfig.class}, new Object[]{senderConfig});
    }

    public static ListenerBootstrap from(ListenerConfig listenerConfig) {
        String bootstrap = listenerConfig.getBootstrap();
        if (StringUtils.isEmpty(bootstrap)) {
            //使用默认监听器
            bootstrap = CrossWayConfigs.getStringValue(CrossWayOptions.DEFAULT_LISTENER);
            listenerConfig.setBootstrap(bootstrap);
        }
        return ExtensionLoaderFactory.getExtensionLoader(ListenerBootstrap.class).getExtension(
            listenerConfig.getBootstrap(), new Class[]{ListenerConfig.class}, new Object[]{listenerConfig});
    }

}
