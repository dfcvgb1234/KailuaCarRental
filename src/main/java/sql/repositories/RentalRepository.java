package sql.repositories;

import sql.SqlComponent;
import sql.components.RentalContract;
import sql.components.cars.Car;

import java.util.List;

public class RentalRepository implements SqlComponent<Integer, RentalContract> {
    @Override
    public RentalContract findFirstById(Integer id) {
        return null;
    }

    @Override
    public List<RentalContract> getAll() {
        return null;
    }

    @Override
    public List<RentalContract> getAmount(int amount) {
        return null;
    }

    @Override
    public void insert(RentalContract component) {

    }

    @Override
    public void insertAll(List<RentalContract> components) {

    }
}
