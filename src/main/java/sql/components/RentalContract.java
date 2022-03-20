package sql.components;

import sql.components.cars.Car;
import sql.components.customers.Customer;

import java.sql.Timestamp;

public class RentalContract {

    private int Id;
    private Car car;
    private Customer customer;
    private Timestamp startDate;
    private Timestamp endDate;
    private int kilometers;

    public RentalContract(int Id, Car car, Customer customer, Timestamp startDate, Timestamp endDate, int kilometers) {
        this.Id = Id;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.kilometers = kilometers;
    }

    public RentalContract(Car car, Customer customer, Timestamp startDate, Timestamp endDate, int kilometers) {
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.kilometers = kilometers;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }
}
