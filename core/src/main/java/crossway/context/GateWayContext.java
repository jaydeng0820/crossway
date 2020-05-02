package crossway.context;

import crossway.message.MessageProvider;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/27
 **/
public class GateWayContext {

    private final ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();

    private String chainId;
    private MessageProvider messageProvider;

    public Object get(String key) {
        return map.get(key);
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public MessageProvider getMessageProvider() {
        if (this.messageProvider == null) {
            this.messageProvider = new MessageProvider();
        }
        return messageProvider;
    }

    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }
}
