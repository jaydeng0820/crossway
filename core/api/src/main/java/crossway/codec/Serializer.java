package crossway.codec;

import crossway.codec.node.Node;
import crossway.ext.api.Extensible;

import java.util.Map;

@Extensible
public interface Serializer {

    Node encode(Object object, Map<String, Object> context);

    Object decode(Node data, Map<String, Object> context);

}
