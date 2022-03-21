package sql;

import java.util.List;

public interface SqlRepository<Key, T> {

    T findFirstById(Key id);
    List<T> getAll();
    List<T> getAmount(int amount);

    void insert(T component);
    void insertAll(List<T> components);

    void deleteOnId(Key id);

}
