package crossway.impl.codec;

import crossway.codec.Serializer;
import crossway.codec.node.Node;
import crossway.ext.api.Extension;
import crossway.impl.codec.node.NullNode;
import crossway.impl.codec.node.TextNode;

import java.util.Map;

@Extension("string")
public class TextSerializer implements Serializer {
    @Override
    public Node encode(Object object, Map<String, Object> context) {
        if (object instanceof String) {
            return new TextNode((String) object);
        }
        return new NullNode();
    }

    @Override
    public Object decode(Node data, Map<String, Object> context) {
        return data.textValue();
    }
}
