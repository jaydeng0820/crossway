package crossway.sofa.rpc.sender;

import crossway.message.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SofaRequest extends Message {
    private static final long serialVersionUID = 1117295573889954506L;

    private Object body;

}
