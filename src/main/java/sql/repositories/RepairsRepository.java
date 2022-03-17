package sql.repositories;

import sql.SqlComponent;
import sql.components.Repair;
import sql.components.cars.Car;

import java.util.List;

public class RepairsRepository implements SqlComponent<Integer, Repair> {
    @Override
    public Repair findFirstById(Integer id) {
        return null;
    }

    @Override
    public List<Repair> getAll() {
        return null;
    }

    @Override
    public List<Repair> getAmount(int amount) {
        return null;
    }

    @Override
    public void insert(Repair component) {

    }

    @Override
    public void insertAll(List<Repair> components) {

    }
}
