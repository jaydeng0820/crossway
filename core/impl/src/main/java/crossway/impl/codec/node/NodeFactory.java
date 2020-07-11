package crossway.impl.codec.node;

import crossway.codec.node.Node;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class NodeFactory implements NodeCreator, Serializable {
    private static final long serialVersionUID = -5792890694292698670L;

    private static final NodeFactory decimalsAsIs = new NodeFactory(true);

    private static final NodeFactory decimalsNormalized = new NodeFactory(false);
    /**
     * Default singleton instance that construct "standard" node instances: given that this class is stateless, a
     * globally shared singleton can be used.
     */
    public static final  NodeFactory instance           = decimalsNormalized;

    private final boolean _cfgBigDecimalExact;

    public NodeFactory(boolean cfgBigDecimalExact) {
        _cfgBigDecimalExact = cfgBigDecimalExact;
    }

    protected NodeFactory() {
        this(false);
    }

    @Override
    public BooleanNode booleanNode(boolean v) {
        return v ? BooleanNode.getTrue() : BooleanNode.getFalse();
    }

    public Node missingNode(){
        return MissingNode.getInstance();
    }

    @Override
    public NullNode nullNode() {
        return NullNode.getInstance();
    }

    @Override
    public NumericNode numberNode(byte v) {
        return null;
    }

    @Override
    public NumericNode numberNode(Byte value) {
        return null;
    }

    @Override
    public NumericNode numberNode(short v) {
        return null;
    }

    @Override
    public NumericNode numberNode(Short value) {
        return null;
    }

    @Override
    public NumericNode numberNode(int v) {
        return null;
    }

    @Override
    public NumericNode numberNode(Integer value) {
        return null;
    }

    @Override
    public NumericNode numberNode(long v) {
        return null;
    }

    @Override
    public NumericNode numberNode(Long value) {
        return null;
    }

    @Override
    public NumericNode numberNode(BigInteger v) {
        return null;
    }

    @Override
    public NumericNode numberNode(float v) {
        return null;
    }

    @Override
    public NumericNode numberNode(Float value) {
        return null;
    }

    @Override
    public NumericNode numberNode(double v) {
        return null;
    }

    @Override
    public NumericNode numberNode(Double value) {
        return null;
    }

    @Override
    public NumericNode numberNode(BigDecimal v) {
        return null;
    }

    @Override
    public TextNode textNode(String text) {
        return new TextNode(text);
    }

    @Override
    public BinaryNode binaryNode(byte[] data) {
        return new BinaryNode(data);
    }

    @Override
    public BinaryNode binaryNode(byte[] data, int offset, int length) {
        return new BinaryNode(data, offset, length);
    }

    @Override
    public ValueNode pojoNode(Object pojo) {
        return null;
    }

    @Override
    public ArrayNode arrayNode() { return new ArrayNode(this); }

    /**
     * Factory method for constructing a JSON Array node with an initial capacity
     *
     */
    @Override
    public ArrayNode arrayNode(int capacity) { return new ArrayNode(this, capacity); }

    /**
     * Factory method for constructing an empty JSON Object ("struct") node
     */
    @Override
    public ObjectNode objectNode() { return new ObjectNode(this); }
}
