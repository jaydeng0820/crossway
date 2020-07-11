package crossway.impl.codec.node;

import crossway.codec.node.NodeType;

public class TextNode extends ValueNode {
    private static final long serialVersionUID = -2533266321458226465L;

    protected final String value;

    public TextNode(String value) {
        this.value = value;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.STRING;
    }

    @Override
    public String asText() {
        return value;
    }

    @Override
    public String textValue() {
        return value;
    }
}
