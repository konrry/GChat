package net.galvin.chat.server.jdbc.comm;

/**
 * Created by galvin on 17-5-27.
 */
public class SqlParam {

    private Integer index;
    private String name;
    private Object value;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
