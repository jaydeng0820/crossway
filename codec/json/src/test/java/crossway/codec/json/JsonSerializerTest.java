package crossway.codec.json;

import crossway.codec.node.Node;
import crossway.impl.codec.node.ArrayNode;
import crossway.impl.codec.node.ObjectNode;
import crossway.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
class JsonSerializerTest {

    @Test
    void encode() {
        Map<String, Object> data = new HashMap<>();
        data.put("int", 1);
        data.put("string", "str");

        List<Object> array = new ArrayList<>();
        array.add(1);
        array.add("11");
        data.put("list", array);

        JsonSerializer jsonSerializer = new JsonSerializer();
        ObjectNode node = (ObjectNode) jsonSerializer.encode(JsonUtils.toString(data), null);
        Assertions.assertEquals(node.get("int").intValue(), 1);
        Assertions.assertEquals(node.get("string").textValue(), "str");
        ArrayNode arrayNode = (ArrayNode) node.get("list");

        System.out.println(jsonSerializer.decode(node, null));
        Assertions.assertEquals(jsonSerializer.decode(node,null), JsonUtils.toString(data));
        
        
    }
}