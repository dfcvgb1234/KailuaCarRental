package sql;

public class SqlParameter<T> {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public SqlParameter(T value) {
        this.value = value;
    }
}
