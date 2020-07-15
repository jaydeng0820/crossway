package crossway.impl.codec.node;

import crossway.codec.node.NodeType;

public class NullNode extends ValueNode {
    public static final  NullNode instance         = new NullNode();
    private static final long     serialVersionUID = -1180865063022870895L;

    public static NullNode getInstance() {
        return instance;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.NULL;
    }

    @Override
    public String asText() {
        return "null";
    }

    @Override
    public String asText(String defaultValue) {
        return defaultValue;
    }
}
