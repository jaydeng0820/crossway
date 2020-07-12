package crossway.codec.node;

import com.fasterxml.jackson.databind.util.ClassUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Node implements Iterable<Node> {

    protected Node() {
    }

    public int size() {
        return 0;
    }

    /**
     * Convenience method that is functionally same as:
     * <pre>
     *    size() == 0
     * </pre>
     * for all node types.
     *
     * @since 2.10
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return the type of this node
     *
     * @return the node type as a {@link NodeType} enum value
     */
    public abstract NodeType getNodeType();

    public abstract Node get(int index);

    public Node get(String fieldName) {
        return null;
    }

    public boolean isObject() {
        return false;
    }

    public Iterator<Node> elements() {
        return Collections.emptyIterator();
    }

    public Iterator<String> fieldNames() {
        return Collections.emptyIterator();
    }

    /**
     * Method that can be used to check if the node is a wrapper for a POJO ("Plain Old Java Object" aka "bean". Returns
     * true only for instances of <code>POJONode</code>.
     *
     * @return True if this node wraps a POJO
     */
    public final boolean isPojo() {
        return getNodeType() == NodeType.POJO;
    }

    /**
     * @return True if this node represents a numeric JSON value
     */
    public final boolean isNumber() {
        return getNodeType() == NodeType.NUMBER;
    }

    /**
     * @return True if this node represents an integral (integer) numeric JSON value
     */
    public boolean isIntegralNumber() {
        return false;
    }

    /**
     * @return True if this node represents a non-integral numeric JSON value
     */
    public boolean isFloatingPointNumber() {
        return false;
    }

    /**
     * Method that can be used to check whether contained value is a number represented as Java <code>short</code>.
     * Note, however, that even if this method returns false, it is possible that conversion would be possible from
     * other numeric types -- to check if this is possible, use {@link #canConvertToInt()} instead.
     *
     * @return True if the value contained by this node is stored as Java short
     */
    public boolean isShort() {
        return false;
    }

    /**
     * Method that can be used to check whether contained value is a number represented as Java <code>int</code>. Note,
     * however, that even if this method returns false, it is possible that conversion would be possible from other
     * numeric types -- to check if this is possible, use {@link #canConvertToInt()} instead.
     *
     * @return True if the value contained by this node is stored as Java int
     */
    public boolean isInt() {
        return false;
    }

    /**
     * Method that can be used to check whether contained value is a number represented as Java <code>long</code>. Note,
     * however, that even if this method returns false, it is possible that conversion would be possible from other
     * numeric types -- to check if this is possible, use {@link #canConvertToLong()} instead.
     *
     * @return True if the value contained by this node is stored as Java <code>long</code>
     */
    public boolean isLong() {
        return false;
    }

    /**
     * @since 2.2
     */
    public boolean isFloat() {
        return false;
    }

    public boolean isDouble() {
        return false;
    }

    public boolean isBigDecimal() {
        return false;
    }

    public boolean isBigInteger() {
        return false;
    }

    /**
     * Method that checks whether this node represents basic JSON String value.
     */
    public final boolean isTextual() {
        return getNodeType() == NodeType.STRING;
    }

    /**
     * Method that can be used to check if this node was created from JSON boolean value (literals "true" and "false").
     */
    public final boolean isBoolean() {
        return getNodeType() == NodeType.BOOLEAN;
    }

    /**
     * Method that can be used to check if this node was created from JSON literal null value.
     */
    public final boolean isNull() {
        return getNodeType() == NodeType.NULL;
    }

    /**
     * Method that can be used to check if this node represents binary data (Base64 encoded). Although this will be
     * externally written as JSON String value, {@link #isTextual} will return false if this method returns true.
     *
     * @return True if this node represents base64 encoded binary data
     */
    public final boolean isBinary() {
        return getNodeType() == NodeType.BINARY;
    }

    public boolean isArray() {
        return false;
    }

    /**
     * Method that can be used to check whether this node is a numeric node ({@link #isNumber} would return true) AND
     * its value fits within Java's 32-bit signed integer type, <code>int</code>. Note that floating-point numbers are
     * convertible if the integral part fits without overflow (as per standard Java coercion rules)
     * <p>
     * NOTE: this method does not consider possible value type conversion from JSON String into Number; so even if this
     * method returns false, it is possible that {@link #asInt} could still succeed if node is a JSON String
     * representing integral number, or boolean.
     *
     * @since 2.0
     */
    public boolean canConvertToInt() {
        return false;
    }

    /**
     * Method that can be used to check whether this node is a numeric node ({@link #isNumber} would return true) AND
     * its value fits within Java's 64-bit signed integer type, <code>long</code>. Note that floating-point numbers are
     * convertible if the integral part fits without overflow (as per standard Java coercion rules)
     * <p>
     * NOTE: this method does not consider possible value type conversion from JSON String into Number; so even if this
     * method returns false, it is possible that {@link #asLong} could still succeed if node is a JSON String
     * representing integral number, or boolean.
     *
     * @since 2.0
     */
    public boolean canConvertToLong() {
        return false;
    }

    /**
     * Method to use for accessing String values. Does <b>NOT</b> do any conversions for non-String value nodes; for
     * non-String values (ones for which {@link #isTextual} returns false) null will be returned. For String values,
     * null is never returned (but empty Strings may be)
     *
     * @return Textual value this node contains, iff it is a textual JSON node (comes from JSON String value entry)
     */
    public String textValue() {
        return null;
    }

    /**
     * Method to use for accessing binary content of binary nodes (nodes for which {@link #isBinary} returns true); or
     * for Text Nodes (ones for which {@link #textValue} returns non-null value), to read decoded base64 data. For other
     * types of nodes, returns null.
     *
     * @return Binary data this node contains, iff it is a binary node; null otherwise
     */
    public byte[] binaryValue() throws IOException {
        return null;
    }

    /**
     * Method to use for accessing JSON boolean values (value literals 'true' and 'false'). For other types, always
     * returns false.
     *
     * @return Textual value this node contains, iff it is a textual json node (comes from JSON String value entry)
     */
    public boolean booleanValue() {
        return false;
    }

    /**
     * Returns numeric value for this node, <b>if and only if</b> this node is numeric ({@link #isNumber} returns true);
     * otherwise returns null
     *
     * @return Number value this node contains, if any (null for non-number nodes).
     */
    public Number numberValue() {
        return null;
    }

    /**
     * Returns 16-bit short value for this node, <b>if and only if</b> this node is numeric ({@link #isNumber} returns
     * true). For other types returns 0. For floating-point numbers, value is truncated using default Java coercion,
     * similar to how cast from double to short operates.
     *
     * @return Short value this node contains, if any; 0 for non-number nodes.
     */
    public short shortValue() {
        return 0;
    }

    /**
     * Returns integer value for this node, <b>if and only if</b> this node is numeric ({@link #isNumber} returns true).
     * For other types returns 0. For floating-point numbers, value is truncated using default Java coercion, similar to
     * how cast from double to int operates.
     *
     * @return Integer value this node contains, if any; 0 for non-number nodes.
     */
    public int intValue() {
        return 0;
    }

    /**
     * Returns 64-bit long value for this node, <b>if and only if</b> this node is numeric ({@link #isNumber} returns
     * true). For other types returns 0. For floating-point numbers, value is truncated using default Java coercion,
     * similar to how cast from double to long operates.
     *
     * @return Long value this node contains, if any; 0 for non-number nodes.
     */
    public long longValue() {
        return 0L;
    }

    /**
     * Returns 32-bit floating value for this node, <b>if and only if</b> this node is numeric ({@link #isNumber}
     * returns true). For other types returns 0.0. For integer values, conversion is done using coercion; this means
     * that an overflow is possible for `long` values
     *
     * @return 32-bit float value this node contains, if any; 0.0 for non-number nodes.
     * @since 2.2
     */
    public float floatValue() {
        return 0.0f;
    }

    /**
     * Returns 64-bit floating point (double) value for this node, <b>if and only if</b> this node is numeric ({@link
     * #isNumber} returns true). For other types returns 0.0. For integer values, conversion is done using coercion;
     * this may result in overflows with {@link BigInteger} values.
     *
     * @return 64-bit double value this node contains, if any; 0.0 for non-number nodes.
     * @since 2.2
     */
    public double doubleValue() {
        return 0.0;
    }

    /**
     * Returns floating point value for this node (as {@link BigDecimal}), <b>if and only if</b> this node is numeric
     * ({@link #isNumber} returns true). For other types returns <code>BigDecimal.ZERO</code>.
     *
     * @return {@link BigDecimal} value this node contains, if numeric node; <code>BigDecimal.ZERO</code> for non-number
     * nodes.
     */
    public BigDecimal decimalValue() {
        return BigDecimal.ZERO;
    }

    /**
     * Returns integer value for this node (as {@link BigDecimal}), <b>if and only if</b> this node is numeric ({@link
     * #isNumber} returns true). For other types returns <code>BigInteger.ZERO</code>.
     *
     * @return {@link BigInteger} value this node contains, if numeric node; <code>BigInteger.ZERO</code> for non-number
     * nodes.
     */
    public BigInteger bigIntegerValue() {
        return BigInteger.ZERO;
    }

    /**
     * Method that will return a valid String representation of the container value, if the node is a value node (method
     * {@link #isValueNode} returns true), otherwise empty String.
     */
    public abstract String asText();

    /**
     * Method similar to {@link #asText()}, except that it will return
     * <code>defaultValue</code> in cases where null value would be returned;
     * either for missing nodes (trying to access missing property, or element at invalid item for array) or explicit
     * nulls.
     *
     * @since 2.4
     */
    public String asText(String defaultValue) {
        String str = asText();
        return (str == null) ? defaultValue : str;
    }

    /**
     * Method that will try to convert value of this node to a Java <b>int</b>. Numbers are coerced using default Java
     * rules; booleans convert to 0 (false) and 1 (true), and Strings are parsed using default Java language integer
     * parsing rules.
     * <p>
     * If representation cannot be converted to an int (including structured types like Objects and Arrays), default
     * value of <b>0</b> will be returned; no exceptions are thrown.
     */
    public int asInt() {
        return asInt(0);
    }

    /**
     * Method that will try to convert value of this node to a Java <b>int</b>. Numbers are coerced using default Java
     * rules; booleans convert to 0 (false) and 1 (true), and Strings are parsed using default Java language integer
     * parsing rules.
     * <p>
     * If representation cannot be converted to an int (including structured types like Objects and Arrays), specified
     * <b>defaultValue</b> will be returned; no exceptions are thrown.
     */
    public int asInt(int defaultValue) {
        return defaultValue;
    }

    /**
     * Method that will try to convert value of this node to a Java <b>long</b>. Numbers are coerced using default Java
     * rules; booleans convert to 0 (false) and 1 (true), and Strings are parsed using default Java language integer
     * parsing rules.
     * <p>
     * If representation cannot be converted to an long (including structured types like Objects and Arrays), default
     * value of <b>0</b> will be returned; no exceptions are thrown.
     */
    public long asLong() {
        return asLong(0L);
    }

    /**
     * Method that will try to convert value of this node to a Java <b>long</b>. Numbers are coerced using default Java
     * rules; booleans convert to 0 (false) and 1 (true), and Strings are parsed using default Java language integer
     * parsing rules.
     * <p>
     * If representation cannot be converted to an long (including structured types like Objects and Arrays), specified
     * <b>defaultValue</b> will be returned; no exceptions are thrown.
     */
    public long asLong(long defaultValue) {
        return defaultValue;
    }

    /**
     * Method that will try to convert value of this node to a Java <b>double</b>. Numbers are coerced using default
     * Java rules; booleans convert to 0.0 (false) and 1.0 (true), and Strings are parsed using default Java language
     * integer parsing rules.
     * <p>
     * If representation cannot be converted to an int (including structured types like Objects and Arrays), default
     * value of <b>0.0</b> will be returned; no exceptions are thrown.
     */
    public double asDouble() {
        return asDouble(0.0);
    }

    /**
     * Method that will try to convert value of this node to a Java <b>double</b>. Numbers are coerced using default
     * Java rules; booleans convert to 0.0 (false) and 1.0 (true), and Strings are parsed using default Java language
     * integer parsing rules.
     * <p>
     * If representation cannot be converted to an int (including structured types like Objects and Arrays), specified
     * <b>defaultValue</b> will be returned; no exceptions are thrown.
     */
    public double asDouble(double defaultValue) {
        return defaultValue;
    }

    /**
     * Method that will try to convert value of this node to a Java <b>boolean</b>. JSON booleans map naturally; integer
     * numbers other than 0 map to true, and 0 maps to false and Strings 'true' and 'false' map to corresponding
     * values.
     * <p>
     * If representation cannot be converted to a boolean value (including structured types like Objects and Arrays),
     * default value of <b>false</b> will be returned; no exceptions are thrown.
     */
    public boolean asBoolean() {
        return asBoolean(false);
    }

    /**
     * Method that will try to convert value of this node to a Java <b>boolean</b>. JSON booleans map naturally; integer
     * numbers other than 0 map to true, and 0 maps to false and Strings 'true' and 'false' map to corresponding
     * values.
     * <p>
     * If representation cannot be converted to a boolean value (including structured types like Objects and Arrays),
     * specified <b>defaultValue</b> will be returned; no exceptions are thrown.
     */
    public boolean asBoolean(boolean defaultValue) {
        return defaultValue;
    }

    public abstract Node path(String fieldName);

    public abstract Node path(int index);

    public final boolean isValueNode() {
        switch (getNodeType()) {
            case ARRAY:
            case OBJECT:
            case MISSING:
                return false;
            default:
                return true;
        }
    }

    public abstract Node findParent(String fieldName);

    public final List<Node> findParents(String fieldName) {
        List<Node> result = findParents(fieldName, null);
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    public abstract List<Node> findValues(String fieldName, List<Node> foundSoFar);

    public abstract List<String> findValuesAsText(String fieldName, List<String> foundSoFar);

    public abstract List<Node> findParents(String fieldName, List<Node> foundSoFar);

    public abstract Node findValue(String fieldName);

    public boolean equals(Comparator<Node> comparator, Node other) {
        return comparator.compare(this, other) == 0;
    }

    public <T extends Node> T withArray(String propertyName) {
        throw new UnsupportedOperationException(
            "Node not of type ObjectNode (but " + getClass().getName() + "), cannot call withArray() on it");
    }

    public <T extends Node> T with(String propertyName) {
        throw new UnsupportedOperationException(
            "JsonNode not of type ObjectNode (but " + getClass().getName() + "), cannot call with() on it");
    }

    public Iterator<Map.Entry<String, Node>> fields() {
        return ClassUtil.emptyIterator();
    }

    public Node required(String fieldName) throws IllegalArgumentException {
        return reportRequiredViolation("Node of type `%s` has no fields", getClass().getName());
    }

    protected <T> T reportRequiredViolation(String msgTemplate, Object... args) {
        throw new IllegalArgumentException(String.format(msgTemplate, args));
    }

    public boolean has(int index) {
        return get(index) != null;
    }

    public boolean hasNonNull(String fieldName) {
        Node n = get(fieldName);
        return (n != null) && !n.isNull();
    }

    public boolean hasNonNull(int index) {
        Node n = get(index);
        return (n != null) && !n.isNull();
    }

    public boolean has(String fieldName) {
        return get(fieldName) != null;
    }

    @Override
    public final Iterator<Node> iterator() { return elements(); }

}