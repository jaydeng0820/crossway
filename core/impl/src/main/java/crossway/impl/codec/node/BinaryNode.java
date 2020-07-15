package crossway.impl.codec.node;

import com.fasterxml.jackson.core.Base64Variants;
import crossway.codec.node.NodeType;

public class BinaryNode extends ValueNode {

    protected final byte[] data;

    public BinaryNode(byte[] data) {
        this.data = data;
    }

    public BinaryNode(byte[] data, int offset, int length) {
        if (offset == 0 && length == data.length) {
            this.data = data;
        } else {
            this.data = new byte[length];
            System.arraycopy(data, offset, this.data, 0, length);
        }
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.BINARY;
    }

    @Override
    public String asText() {
        return Base64Variants.getDefaultVariant().encode(data, false);
    }
}
