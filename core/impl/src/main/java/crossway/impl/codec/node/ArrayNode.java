package crossway.impl.codec.node;


import crossway.codec.node.Node;
import crossway.codec.node.NodeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArrayNode extends ContainerNode<ArrayNode> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Node> children;

    public ArrayNode(List<Node> children) {
        this.children = children;
    }

    public ArrayNode(NodeFactory nf) {
        super(nf);
        children = new ArrayList<Node>();
    }

    @Override
    public int size() {
        return this.children.size();
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ARRAY;
    }

    @Override
    public Node get(int index) {
        if ((index >= 0) && (index < children.size())) {
            return children.get(index);
        }
        return null;
    }

    @Override
    public Node get(String fieldName) {
        return null;
    }

    @Override
    public Node path(String fieldName) {
        return null;
    }

    @Override
    public Node path(int index) {
        return null;
    }

    @Override
    public Node findParent(String fieldName) {
        return null;
    }

    @Override
    public List<Node> findValues(String fieldName, List<Node> foundSoFar) {
        return null;
    }

    @Override
    public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
        return null;
    }

    @Override
    public List<Node> findParents(String fieldName, List<Node> foundSoFar) {
        return null;
    }

    @Override
    public Node findValue(String fieldName) {
        return null;
    }

    @Override
    public ArrayNode removeAll() {
        return null;
    }

    /**
     * @since 2.8
     */
    public ArrayNode(NodeFactory nf, int capacity) {
        super(nf);
        children = new ArrayList<Node>(capacity);
    }
}
