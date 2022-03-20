package sql.components;

import sql.components.cars.Car;

import java.sql.Timestamp;

public class Repair {

    private Car car;
    private Timestamp startTime;
    private Timestamp endTime;
    private String repairLocation;
    private String note;
    private Mechanic mechanic;

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

    public void setNotes(String note) {
        this.note = note;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Repair(Car car, Timestamp startTime, String repairLocation, String note, Mechanic mechanic) {
        this.car = car;
        this.startTime = startTime;
        this.repairLocation = repairLocation;
        this.note = note;
        this.mechanic = mechanic;
    }

    public Repair(Car car, Timestamp startTime, Timestamp endTime, String repairLocation, String note, Mechanic mechanic) {
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repairLocation = repairLocation;
        this.note = note;
        this.mechanic = mechanic;
    }
}
