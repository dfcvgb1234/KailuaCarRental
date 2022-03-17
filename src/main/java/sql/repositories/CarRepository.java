package sql.repositories;

import sql.SqlComponent;
import sql.components.cars.Car;

import java.util.List;

public class CarRepository implements SqlComponent<String, Car> {
    @Override
    public Car findFirstById(String id) {
        return null;
    }

    @Override
    public List<Car> getAll() {
        return null;
    }

    @Override
    public List<Car> getAmount(int amount) {
        return null;
    }

    @Override
    public void insert(Car component) {

    }

    @Override
    public void insertAll(List<Car> components) {

    }
}
