package crossway.message;

import crossway.utils.JsonUtils;

import java.io.Serializable;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/29
 **/
public class Message implements Serializable {
    private static final long serialVersionUID = -1017248745957239524L;

    @Override
    public String toString() {
        return JsonUtils.toString(this);
    }

}
