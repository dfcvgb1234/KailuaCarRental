package sql.repositories;

import sql.SqlComponent;
import sql.components.customers.Customer;

import java.util.List;

public class CustomerRepository implements SqlComponent<Integer, Customer> {

    @Override
    public Customer findFirstById(Integer id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public List<Customer> getAmount(int amount) {
        return null;
    }

    @Override
    public void insert(Customer component) {

    }

    @Override
    public void insertAll(List<Customer> components) {

    }
}
