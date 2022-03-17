package sql;

public class SqlParameter<T> {
    private T value;
    private Class<T> type;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public SqlParameter(T value, Class<T> type) {
        this.value = value;
        this.type = type;
    }
}
