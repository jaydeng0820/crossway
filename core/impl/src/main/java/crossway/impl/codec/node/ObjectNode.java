package crossway.impl.codec.node;


import crossway.codec.node.Node;
import crossway.codec.node.NodeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ObjectNode extends ContainerNode<ObjectNode> implements Serializable {
    private static final long serialVersionUID = 1L; // since 2.10

    // Note: LinkedHashMap for backwards compatibility
    protected final Map<String, Node> _children;

    public ObjectNode(NodeFactory nc) {
        super(nc);
        _children = new LinkedHashMap<String, Node>();
    }

    /**
     * @since 2.4
     */
    public ObjectNode(NodeFactory nc, Map<String, Node> kids) {
        super(nc);
        _children = kids;
    }

    /*
    /**********************************************************
    /* Implementation of core Node API
    /**********************************************************
     */

    @Override
    public NodeType getNodeType() {
        return NodeType.OBJECT;
    }

    @Override
    public final boolean isObject() {
        return true;
    }

    @Override
    public int size() {
        return _children.size();
    }

    @Override // since 2.10
    public boolean isEmpty() {
        return _children.isEmpty();
    }

    @Override
    public Iterator<Node> elements() {
        return _children.values().iterator();
    }

    @Override
    public Node get(int index) {
        return null;
    }

    @Override
    public Node get(String fieldName) {
        return _children.get(fieldName);
    }

    @Override
    public Iterator<String> fieldNames() {
        return _children.keySet().iterator();
    }

    @Override
    public Node path(int index) {
        return MissingNode.getInstance();
    }

    @Override
    public Node path(String fieldName) {
        Node n = _children.get(fieldName);
        if (n != null) {
            return n;
        }
        return MissingNode.getInstance();
    }

    @Override
    public Node required(String fieldName) {
        Node n = _children.get(fieldName);
        if (n != null) {
            return n;
        }
        return reportRequiredViolation("No value for property '%s' of `ObjectNode`", fieldName);
    }

    /**
     * Method to use for accessing all fields (with both names and values) of this JSON Object.
     */
    @Override
    public Iterator<Map.Entry<String, Node>> fields() {
        return _children.entrySet().iterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ObjectNode with(String propertyName) {
        Node n = _children.get(propertyName);
        if (n != null) {
            if (n instanceof ObjectNode) {
                return (ObjectNode) n;
            }
            throw new UnsupportedOperationException(
                "Property '" + propertyName + "' has value that is not of type ObjectNode (but "
                + n.getClass().getName() + ")");
        }
        ObjectNode result = objectNode();
        _children.put(propertyName, result);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayNode withArray(String propertyName) {
        Node n = _children.get(propertyName);
        if (n != null) {
            if (n instanceof ArrayNode) {
                return (ArrayNode) n;
            }
            throw new UnsupportedOperationException(
                "Property '" + propertyName + "' has value that is not of type ArrayNode (but " + n.getClass().getName()
                + ")");
        }
        ArrayNode result = arrayNode();
        _children.put(propertyName, result);
        return result;
    }

    @Override
    public boolean equals(Comparator<Node> comparator, Node o) {
        if (!(o instanceof ObjectNode)) {
            return false;
        }
        ObjectNode other = (ObjectNode) o;
        Map<String, Node> m1 = _children;
        Map<String, Node> m2 = other._children;

        final int len = m1.size();
        if (m2.size() != len) {
            return false;
        }

        for (Map.Entry<String, Node> entry : m1.entrySet()) {
            Node v2 = m2.get(entry.getKey());
            if ((v2 == null) || !entry.getValue().equals(comparator, v2)) {
                return false;
            }
        }
        return true;
    }

    /*
    /**********************************************************
    /* Public API, finding value nodes
    /**********************************************************
     */

    @Override
    public Node findValue(String fieldName) {
        for (Map.Entry<String, Node> entry : _children.entrySet()) {
            if (fieldName.equals(entry.getKey())) {
                return entry.getValue();
            }
            Node value = entry.getValue().findValue(fieldName);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    public List<Node> findValues(String fieldName, List<Node> foundSoFar) {
        for (Map.Entry<String, Node> entry : _children.entrySet()) {
            if (fieldName.equals(entry.getKey())) {
                if (foundSoFar == null) {
                    foundSoFar = new ArrayList<Node>();
                }
                foundSoFar.add(entry.getValue());
            } else { // only add children if parent not added
                foundSoFar = entry.getValue().findValues(fieldName, foundSoFar);
            }
        }
        return foundSoFar;
    }

    @Override
    public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
        for (Map.Entry<String, Node> entry : _children.entrySet()) {
            if (fieldName.equals(entry.getKey())) {
                if (foundSoFar == null) {
                    foundSoFar = new ArrayList<String>();
                }
                foundSoFar.add(entry.getValue().asText());
            } else { // only add children if parent not added
                foundSoFar = entry.getValue().findValuesAsText(fieldName, foundSoFar);
            }
        }
        return foundSoFar;
    }

    @Override
    public ObjectNode findParent(String fieldName) {
        for (Map.Entry<String, Node> entry : _children.entrySet()) {
            if (fieldName.equals(entry.getKey())) {
                return this;
            }
            Node value = entry.getValue().findParent(fieldName);
            if (value != null) {
                return (ObjectNode) value;
            }
        }
        return null;
    }

    @Override
    public List<Node> findParents(String fieldName, List<Node> foundSoFar) {
        for (Map.Entry<String, Node> entry : _children.entrySet()) {
            if (fieldName.equals(entry.getKey())) {
                if (foundSoFar == null) {
                    foundSoFar = new ArrayList<Node>();
                }
                foundSoFar.add(this);
            } else { // only add children if parent not added
                foundSoFar = entry.getValue().findParents(fieldName, foundSoFar);
            }
        }
        return foundSoFar;
    }

    /*
    /**********************************************************
    /* Public API, serialization
    /**********************************************************
     */


    /*
    /**********************************************************
    /* Extended ObjectNode API, mutators, since 2.1
    /**********************************************************
     */

    /**
     * Method that will set specified field, replacing old value, if any. Note that this is identical to {@link
     * #replace(String, Node)}, except for return value.
     * <p>
     * NOTE: added to replace those uses of {@link #put(String, Node)} where chaining with 'this' is desired.
     * <p>
     * NOTE: co-variant return type since 2.10
     *
     * @param value
     *     to set field to; if null, will be converted to a {@link NullNode} first  (to remove field entry, call {@link
     *     #remove} instead)
     *
     * @return This node after adding/replacing property value (to allow chaining)
     * @since 2.1
     */
    @SuppressWarnings("unchecked")
    public <T extends Node> T set(String fieldName, Node value) {
        if (value == null) {
            value = nullNode();
        }
        _children.put(fieldName, value);
        return (T) this;
    }

    /**
     * Method for adding given properties to this object node, overriding any existing values for those properties.
     * <p>
     * NOTE: co-variant return type since 2.10
     *
     * @param properties
     *     Properties to add
     *
     * @return This node after adding/replacing property values (to allow chaining)
     * @since 2.1
     */
    @SuppressWarnings("unchecked")
    public <T extends Node> T setAll(Map<String, ? extends Node> properties) {
        for (Map.Entry<String, ? extends Node> en : properties.entrySet()) {
            Node n = en.getValue();
            if (n == null) {
                n = nullNode();
            }
            _children.put(en.getKey(), n);
        }
        return (T) this;
    }

    /**
     * Method for adding all properties of the given Object, overriding any existing values for those properties.
     * <p>
     * NOTE: co-variant return type since 2.10
     *
     * @param other
     *     Object of which properties to add to this object
     *
     * @return This node after addition (to allow chaining)
     * @since 2.1
     */
    @SuppressWarnings("unchecked")
    public <T extends Node> T setAll(ObjectNode other) {
        _children.putAll(other._children);
        return (T) this;
    }

    /**
     * Method for replacing value of specific property with passed value, and returning value (or null if none).
     *
     * @param fieldName
     *     Property of which value to replace
     * @param value
     *     Value to set property to, replacing old value if any
     *
     * @return Old value of the property; null if there was no such property with value
     * @since 2.1
     */
    public Node replace(String fieldName, Node value) {
        if (value == null) { // let's not store 'raw' nulls but nodes
            value = nullNode();
        }
        return _children.put(fieldName, value);
    }

    /**
     * Method for removing field entry from this ObjectNode, and returning instance after removal.
     * <p>
     * NOTE: co-variant return type since 2.10
     *
     * @return This node after removing entry (if any)
     * @since 2.1
     */
    @SuppressWarnings("unchecked")
    public <T extends Node> T without(String fieldName) {
        _children.remove(fieldName);
        return (T) this;
    }

    /**
     * Method for removing specified field properties out of this ObjectNode.
     * <p>
     * NOTE: co-variant return type since 2.10
     *
     * @param fieldNames
     *     Names of fields to remove
     *
     * @return This node after removing entries
     * @since 2.1
     */
    @SuppressWarnings("unchecked")
    public <T extends Node> T without(Collection<String> fieldNames) {
        _children.keySet().removeAll(fieldNames);
        return (T) this;
    }

    /*
    /**********************************************************
    /* Extended ObjectNode API, mutators, generic
    /**********************************************************
     */

    /**
     * Method that will set specified field, replacing old value, if any.
     *
     * @param value
     *     to set field to; if null, will be converted to a {@link NullNode} first  (to remove field entry, call {@link
     *     #remove} instead)
     *
     * @return Old value of the field, if any; null if there was no old value.
     * @deprecated Since 2.4 use either {@link #set(String, Node)} or {@link #replace(String, Node)},
     */
    @Deprecated
    public Node put(String fieldName, Node value) {
        if (value == null) { // let's not store 'raw' nulls but nodes
            value = nullNode();
        }
        return _children.put(fieldName, value);
    }

    /**
     * Method for removing field entry from this ObjectNode. Will return value of the field, if such field existed; null
     * if not.
     *
     * @return Value of specified field, if it existed; null if not
     */
    public Node remove(String fieldName) {
        return _children.remove(fieldName);
    }

    /**
     * Method for removing specified field properties out of this ObjectNode.
     *
     * @param fieldNames
     *     Names of fields to remove
     *
     * @return This node after removing entries
     */
    public ObjectNode remove(Collection<String> fieldNames) {
        _children.keySet().removeAll(fieldNames);
        return this;
    }

    /**
     * Method for removing all field properties, such that this ObjectNode will contain no properties after call.
     *
     * @return This node after removing all entries
     */
    @Override
    public ObjectNode removeAll() {
        _children.clear();
        return this;
    }

    /**
     * Method for adding given properties to this object node, overriding any existing values for those properties.
     *
     * @param properties
     *     Properties to add
     *
     * @return This node after adding/replacing property values (to allow chaining)
     * @deprecated Since 2.4 use {@link #setAll(Map)},
     */
    @Deprecated
    public Node putAll(Map<String, ? extends Node> properties) {
        return setAll(properties);
    }

    /**
     * Method for adding all properties of the given Object, overriding any existing values for those properties.
     *
     * @param other
     *     Object of which properties to add to this object
     *
     * @return This node (to allow chaining)
     * @deprecated Since 2.4 use {@link #setAll(ObjectNode)},
     */
    @Deprecated
    public Node putAll(ObjectNode other) {
        return setAll(other);
    }

    /**
     * Method for removing all field properties out of this ObjectNode
     * <b>except</b> for ones specified in argument.
     *
     * @param fieldNames
     *     Fields to <b>retain</b> in this ObjectNode
     *
     * @return This node (to allow call chaining)
     */
    public ObjectNode retain(Collection<String> fieldNames) {
        _children.keySet().retainAll(fieldNames);
        return this;
    }

    /**
     * Method for removing all field properties out of this ObjectNode
     * <b>except</b> for ones specified in argument.
     *
     * @param fieldNames
     *     Fields to <b>retain</b> in this ObjectNode
     *
     * @return This node (to allow call chaining)
     */
    public ObjectNode retain(String... fieldNames) {
        return retain(Arrays.asList(fieldNames));
    }

    /*
    /**********************************************************
    /* Extended ObjectNode API, mutators, typed
    /**********************************************************
     */

    /**
     * Method that will construct an ArrayNode and add it as a field of this ObjectNode, replacing old value, if any.
     * <p>
     * <b>NOTE</b>: Unlike all <b>put(...)</b> methods, return value
     * is <b>NOT</b> this <code>ObjectNode</code>, but the
     * <b>newly created</b> <code>ArrayNode</code> instance.
     *
     * @return Newly constructed ArrayNode (NOT the old value, which could be of any type)
     */
    public ArrayNode putArray(String fieldName) {
        ArrayNode n = arrayNode();
        _put(fieldName, n);
        return n;
    }

    /**
     * Method that will construct an ObjectNode and add it as a field of this ObjectNode, replacing old value, if any.
     * <p>
     * <b>NOTE</b>: Unlike all <b>put(...)</b> methods, return value
     * is <b>NOT</b> this <code>ObjectNode</code>, but the
     * <b>newly created</b> <code>ObjectNode</code> instance.
     *
     * @return Newly constructed ObjectNode (NOT the old value, which could be of any type)
     */
    public ObjectNode putObject(String fieldName) {
        ObjectNode n = objectNode();
        _put(fieldName, n);
        return n;
    }

    /**
     * @return This node (to allow chaining)
     */
    public ObjectNode putPOJO(String fieldName, Object pojo) {
        return _put(fieldName, pojoNode(pojo));
    }

    /**
     * @return This node (to allow chaining)
     */
    public ObjectNode putNull(String fieldName) {
        _children.put(fieldName, nullNode());
        return this;
    }

    /**
     * Method for setting value of a field to specified numeric value.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, short v) {
        return _put(fieldName, numberNode(v));
    }

    /**
     * Alternative method that we need to avoid bumping into NPE issues with auto-unboxing.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, Short v) {
        return _put(fieldName, (v == null) ? nullNode() : numberNode(v.shortValue()));
    }

    public ObjectNode put(String fieldName, int v) {
        return _put(fieldName, numberNode(v));
    }

    /**
     * Alternative method that we need to avoid bumping into NPE issues with auto-unboxing.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, Integer v) {
        return _put(fieldName, (v == null) ? nullNode() : numberNode(v.intValue()));
    }

    public ObjectNode put(String fieldName, long v) {
        return _put(fieldName, numberNode(v));
    }

    public ObjectNode put(String fieldName, Long v) {
        return _put(fieldName, (v == null) ? nullNode() : numberNode(v.longValue()));
    }

    /**
     * Method for setting value of a field to specified numeric value.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, float v) {
        return _put(fieldName, numberNode(v));
    }

    /**
     * Alternative method that we need to avoid bumping into NPE issues with auto-unboxing.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, Float v) {
        return _put(fieldName, (v == null) ? nullNode() : numberNode(v.floatValue()));
    }

    /**
     * Method for setting value of a field to specified numeric value.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, double v) {
        return _put(fieldName, numberNode(v));
    }

    /**
     * Alternative method that we need to avoid bumping into NPE issues with auto-unboxing.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, Double v) {
        return _put(fieldName, (v == null) ? nullNode() : numberNode(v.doubleValue()));
    }

    /**
     * Method for setting value of a field to specified numeric value.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, BigDecimal v) {
        return _put(fieldName, (v == null) ? nullNode() : numberNode(v));
    }

    /**
     * Method for setting value of a field to specified numeric value.
     *
     * @return This node (to allow chaining)
     * @since 2.9
     */
    public ObjectNode put(String fieldName, BigInteger v) {
        return _put(fieldName, (v == null) ? nullNode() : numberNode(v));
    }

    /**
     * Method for setting value of a field to specified String value.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, String v) {
        return _put(fieldName, (v == null) ? nullNode() : textNode(v));
    }

    /**
     * Method for setting value of a field to specified String value.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, boolean v) {
        return _put(fieldName, booleanNode(v));
    }

    /**
     * Alternative method that we need to avoid bumping into NPE issues with auto-unboxing.
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, Boolean v) {
        return _put(fieldName, (v == null) ? nullNode() : booleanNode(v.booleanValue()));
    }

    /**
     * Method for setting value of a field to specified binary value
     *
     * @return This node (to allow chaining)
     */
    public ObjectNode put(String fieldName, byte[] v) {
        return _put(fieldName, (v == null) ? nullNode() : binaryNode(v));
    }

    /*
    /**********************************************************
    /* Standard methods
    /**********************************************************
     */

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof ObjectNode) {
            return _childrenEqual((ObjectNode) o);
        }
        return false;
    }

    /**
     * @since 2.3
     */
    protected boolean _childrenEqual(ObjectNode other) {
        return _children.equals(other._children);
    }

    @Override
    public int hashCode() {
        return _children.hashCode();
    }

    /*
    /**********************************************************
    /* Internal methods (overridable)
    /**********************************************************
     */

    protected ObjectNode _put(String fieldName, Node value) {
        _children.put(fieldName, value);
        return this;
    }
}
