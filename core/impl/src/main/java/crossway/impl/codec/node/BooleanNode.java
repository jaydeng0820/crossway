package crossway.impl.codec.node;


import crossway.codec.node.NodeType;

public class BooleanNode extends ValueNode {

    public final static BooleanNode TRUE = new BooleanNode(true);

    public final static BooleanNode FALSE = new BooleanNode(false);

    private static final long serialVersionUID = -2928510850133750165L;

    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public static BooleanNode getTrue() {
        return TRUE;
    }

    public static BooleanNode getFalse() {
        return FALSE;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.BOOLEAN;
    }

    @Override
    public String asText() {
        return value ? "true" : "false";
    }

    @Override
    public boolean asBoolean() {
        return this.value;
    }
}
