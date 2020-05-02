package crossway.model;

import java.util.HashMap;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/28
 **/
public class AbstractCfMod extends HashMap<String, String> {

    public static final String PROPERTY_ENV = "env";

    private final String PROFILE = "profile";
    private final String MINPOOLSIZE = "minpoolsize";
    private final String MAXPOOLSIZE = "maxpoolsize";
    private final String LISTENERCONVERTER = "listenerConverter";
    private final String SENDERCONVERTER = "senderConverter";

    public String getListenerConverter() {
        return this.get(this.LISTENERCONVERTER);
    }

    public int getMaxPoolSize() {
        return Integer.valueOf(this.get(this.MAXPOOLSIZE));
    }

    public int getMinPoolSize() {
        return Integer.valueOf(this.get(this.MINPOOLSIZE));
    }

    public String getProfile() {
        return this.get(this.PROFILE);
    }

    public String getSenderConverter() {
        return this.get(this.SENDERCONVERTER);
    }

}
