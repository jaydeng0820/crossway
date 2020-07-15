package crossway.impl.codec.node;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface NodeCreator {
    // Enumerated/singleton types

    public ValueNode booleanNode(boolean v);

    public ValueNode nullNode();

    public ValueNode numberNode(byte v);

    public ValueNode numberNode(Byte value);

    public ValueNode numberNode(short v);

    public ValueNode numberNode(Short value);

    public ValueNode numberNode(int v);

    public ValueNode numberNode(Integer value);

    public ValueNode numberNode(long v);

    public ValueNode numberNode(Long value);

    public ValueNode numberNode(BigInteger v);

    public ValueNode numberNode(float v);

    public ValueNode numberNode(Float value);

    public ValueNode numberNode(double v);

    public ValueNode numberNode(Double value);

    public ValueNode numberNode(BigDecimal v);

    // Textual nodes

    public ValueNode textNode(String text);

    // Other value (non-structured) nodes

    public ValueNode binaryNode(byte[] data);

    public ValueNode binaryNode(byte[] data, int offset, int length);

    public ValueNode pojoNode(Object pojo);

    public ArrayNode arrayNode();

    public ArrayNode arrayNode(int capacity);

    public ObjectNode objectNode();


}
