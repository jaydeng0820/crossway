package crossway.impl.codec.node;


import crossway.codec.node.NodeType;
import crossway.utils.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class IntNode extends NumericNode {

    final static int MIN_CANONICAL = -1;
    final static int MAX_CANONICAL = 10;

    private final static com.fasterxml.jackson.databind.node.IntNode[] CANONICALS;

    static {
        int count = MAX_CANONICAL - MIN_CANONICAL + 1;
        CANONICALS = new com.fasterxml.jackson.databind.node.IntNode[count];
        for (int i = 0; i < count; ++i) {
            CANONICALS[i] = new com.fasterxml.jackson.databind.node.IntNode(MIN_CANONICAL + i);
        }
    }

    /**
     * Integer value this node contains
     */
    protected final int value;

    public IntNode(int value) {
        this.value = value;
    }


    @Override
    public Number numberValue() {
        return null;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return 0;
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
        return StringUtils.toString(this.value);
    }

    @Override
    public boolean isInt() {
        return true;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.NUMBER;
    }

    @Override
    public boolean canConvertToInt() {
        return false;
    }

    @Override
    public boolean canConvertToLong() {
        return false;
    }
}
