package crossway.sofa.rpc.api;

import crossway.message.Message;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/29
 **/
@Data
public class RpcMsg extends Message {
    private static final long serialVersionUID = 3822378778932981538L;

    private Map<String, Object> param = new HashMap<>();

}
