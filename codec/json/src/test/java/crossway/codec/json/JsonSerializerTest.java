package crossway.codec.json;

import crossway.codec.node.Node;
import crossway.impl.codec.node.ObjectNode;
import crossway.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest {

    @Test
    void encode() {
        Map<String,Object> data = new HashMap<>();
        data.put("int", 1);
        data.put("string", "str");

        List<Object> array = new ArrayList<>();
        array.add(1);
        array.add("11");
        data.put("list", array);

        JsonSerializer jsonSerializer = new JsonSerializer();
       Node node = jsonSerializer.encode(JsonUtils.toString(data), null);
       if (node instanceof ObjectNode) {
           Assertions.assertEquals(node.get("int").intValue(), 1);
           Assertions.assertEquals(node.get("string").textValue(), "str");
       }
    }
}