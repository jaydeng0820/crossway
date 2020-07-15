package crossway.impl.codec.node;


import crossway.codec.node.Node;
import crossway.codec.node.NodeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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

    /**
     * @since 2.8
     */
    public ArrayNode(NodeFactory nf, int capacity) {
        super(nf);
        children = new ArrayList<Node>(capacity);
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
        return MissingNode.getInstance();
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

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public Iterator<Node> elements() {
        return this.children.iterator();
    }

    /**
     * Method for adding specified node at the end of this array.
     *
     * @return This node, to allow chaining
     */
    public ArrayNode add(Node value) {
        if (value == null) { // let's not store 'raw' nulls but nodes
            value = nullNode();
        }
        _add(value);
        return this;
    }

    protected ArrayNode _add(Node node) {
        children.add(node);
        return this;
    }
}
