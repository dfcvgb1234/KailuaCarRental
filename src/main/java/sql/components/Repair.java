package sql.components;

import sql.components.cars.Car;

import java.sql.Timestamp;

public class Repair {

    private int Id;
    private Car car;
    private Timestamp startTime;
    private Timestamp endTime;
    private String repairLocation;
    private String note;
    private double cost;
    private Mechanic mechanic;

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getRepairLocation() {
        return repairLocation;
    }

    public void setRepairLocation(String location) {
        this.repairLocation = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public int getId() {
        return Id;
    }

    public Repair(int id, Car car, Timestamp startTime, String repairLocation, String note, double cost, Mechanic mechanic) {
        this.car = car;
        this.startTime = startTime;
        this.repairLocation = repairLocation;
        this.note = note;
        this.cost = cost;
        this.mechanic = mechanic;
    }

    public Repair(int id, Car car, Timestamp startTime, Timestamp endTime, String repairLocation, String note, double cost, Mechanic mechanic) {
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repairLocation = repairLocation;
        this.note = note;
        this.cost = cost;
        this.mechanic = mechanic;
    }

    public Repair(Car car, String repairLocation, String note, double cost, Mechanic mechanic){
        this.car = car;
        this.repairLocation = repairLocation;
        this.note = note;
        this.cost = cost;
        this.mechanic = mechanic;
    }

    @Override
    public String toString() {
        return  "Mechanic: " + mechanic.getName() + "\n"+
                "Car: " + car.getRegNumber() + "\n" +
                "Repair location: " + repairLocation + "\n" +
                "Note: " + note + "\n" +
                "Estimated cost: " + cost + "\n";
    }
}
