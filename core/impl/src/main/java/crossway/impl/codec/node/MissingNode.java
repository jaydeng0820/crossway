package crossway.impl.codec.node;

import crossway.codec.node.NodeType;

public class MissingNode extends ValueNode {
    private static final long serialVersionUID = -805311459097189311L;

    private static final MissingNode instance = new MissingNode();

    public static MissingNode getInstance() {
        return instance;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.MISSING;
    }

    @Override
    public String asText() {
        return "";
    }
}
