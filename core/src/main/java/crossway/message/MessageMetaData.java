package crossway.message;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Johnson Wang
 * @version 6.0.0
 * @date 2020/2/28
 **/
@Data
public class MessageMetaData implements Serializable {

    private static final long serialVersionUID = -3753547903082718298L;

    private String messageName;

    private String appId;

    private String userId;

    private String facilityId;

    private String method;

    private String format;

    private String charset;

    private Date timestamp;

    private String version;

    private String transactionId;

}
