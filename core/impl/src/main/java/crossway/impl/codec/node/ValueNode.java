package crossway.impl.codec.node;

import crossway.codec.node.Node;

import java.util.List;

public abstract class ValueNode extends BaseNode {
    private static final long serialVersionUID = 1L;

    protected ValueNode() {
    }


    /*
    /**********************************************************************
    /* Basic property access
    /**********************************************************************
     */

    @Override
    public boolean isEmpty() {
        return true;
    }

    /*
    /**********************************************************************
    /* Navigation methods
    /**********************************************************************
     */

    @Override
    public final Node get(int index) {
        return null;
    }

    @Override
    public final Node path(int index) {
        return MissingNode.getInstance();
    }

    @Override
    public final boolean has(int index) {
        return false;
    }

    @Override
    public final boolean hasNonNull(int index) {
        return false;
    }

    @Override
    public final Node get(String fieldName) {
        return null;
    }

    @Override
    public final Node path(String fieldName) {
        return MissingNode.getInstance();
    }

    @Override
    public final boolean has(String fieldName) {
        return false;
    }

    @Override
    public final boolean hasNonNull(String fieldName) {
        return false;
    }

    /*
     **********************************************************************
     * Find methods: all "leaf" nodes return the same for these
     **********************************************************************
     */

    @Override
    public final Node findValue(String fieldName) {
        return null;
    }

    // note: co-variant return type
    @Override
    public final ObjectNode findParent(String fieldName) {
        return null;
    }

    @Override
    public final List<Node> findValues(String fieldName, List<Node> foundSoFar) {
        return foundSoFar;
    }

    @Override
    public final List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
        return foundSoFar;
    }

    @Override
    public final List<Node> findParents(String fieldName, List<Node> foundSoFar) {
        return foundSoFar;
    }
}
