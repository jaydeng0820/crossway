package crossway.impl.codec.node;

import crossway.codec.node.NodeType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LongNode extends NumericNode {

    private final Long value;

    public LongNode(Long value) {
        this.value = value;
    }

    @Override
    public Number numberValue() {
        return null;
    }

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public long asLong() {
        return this.longValue();
    }

    @Override
    public double doubleValue() {
        return 0;
    }

    @Override
    public BigDecimal decimalValue() {
        return null;
    }

    @Override
    public BigInteger bigIntegerValue() {
        return null;
    }

    @Override
    public String asText() {
        return null;
    }

    @Override
    public NodeType getNodeType() {
        return null;
    }

    @Override
    public boolean canConvertToInt() {
        return false;
    }

    @Override
    public boolean canConvertToLong() {
        return false;
    }

    @Override
    public boolean isLong() {
        return true;
    }
}
