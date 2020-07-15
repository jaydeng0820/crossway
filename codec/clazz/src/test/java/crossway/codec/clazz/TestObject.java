package crossway.codec.clazz;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestObject {

    private String str   = "string";
    private Date   date  = new Date();
    private long   aLong = 111L;
    private int    anInt = 111;

    private TestObject              testObject;
    private List<TestObject>        list;
    private Map<String, TestObject> objectMap;


    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getaLong() {
        return aLong;
    }

    public void setaLong(long aLong) {
        this.aLong = aLong;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public TestObject getTestObject() {
        return testObject;
    }

    public void setTestObject(TestObject testObject) {
        this.testObject = testObject;
    }

    public List<TestObject> getList() {
        return list;
    }

    public void setList(List<TestObject> list) {
        this.list = list;
    }

    public Map<String, TestObject> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, TestObject> objectMap) {
        this.objectMap = objectMap;
    }
}
