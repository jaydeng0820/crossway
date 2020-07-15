package crossway.codec.clazz;

import crossway.impl.codec.node.ObjectNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ClazzSerializerTest {

    @Test
    public void test() {
        ClazzSerializer clazzSerializer = new ClazzSerializer();

        TestObject testObject = new TestObject();

        List<TestObject> list = new ArrayList<>();

        TestObject testObject1 = new TestObject();
        testObject1.setList(Arrays.asList(new TestObject()));
        testObject1.setTestObject(new TestObject());
        list.add(testObject1);

        testObject.setList(list);

        Map<String, TestObject> maps = new HashMap<>();
        maps.put("maoobject", testObject1);

        testObject.setObjectMap(maps);
        testObject.setTestObject(testObject1);

        ObjectNode objectNode = (ObjectNode) clazzSerializer.encode(testObject, null);

        Map<String, Object> context = new HashMap<>();
        context.put("root", "crossway.codec.clazz.TestObject");
        TestObject testObject2 = (TestObject) clazzSerializer.decode(objectNode, context);

        Assertions.assertThat(testObject2).isEqualToComparingFieldByFieldRecursively(testObject);
    }

}