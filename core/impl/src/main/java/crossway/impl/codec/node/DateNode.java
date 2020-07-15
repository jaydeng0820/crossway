package crossway.impl.codec.node;

import crossway.codec.node.NodeType;
import crossway.utils.DateUtils;

import java.util.Date;

public class DateNode extends ValueNode {

    private final Date value;

    public DateNode(Date value) {
        this.value = value;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.DATE;
    }

    @Override
    public String asText() {
        return DateUtils.dateToStr(this.value);
    }

    @Override
    public Date dateValue() {
        return this.value;
    }
}
