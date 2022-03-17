package cars;

import rest.DMRCar;

import java.util.ArrayList;
import java.util.Date;

public abstract class Car {

    private String brand;
    private String model;
    private String variant;
    private String fuelType;
    private Date regDate;
    private String regNumber;
    private int odometer;
    private Engine engineConfiguration;
    private ArrayList<String> carFeatures;

    public Car(DMRCar newCar) {
        this.brand = newCar.getMaerkeTypeNavn();
        this.model = newCar.getModelTypeNavn();
        this.variant = newCar.getVariantTypeNavn();
        this.fuelType = newCar.getDrivkraftTypeNavn();
        this.regDate = newCar.getFoersteRegistreringDato();
        this.regNumber = newCar.getRegNr();
        this.odometer = newCar.getMotorKilometerstand();
        this.engineConfiguration = new Engine((int)newCar.getMotorSlagVolumen(), newCar.getMotorHestekraefter());
        this.carFeatures = newCar.getKoeretoejUdstyrSamling();
    }

    public Car() {

    }

    public static class Builder {

        private String brand;
        private String model;
        private String variant;
        private String fuelType;
        private Date regDate;
        private String regNumber;
        private int odometer;
        private Engine engineConfiguration;
        private ArrayList<String> carFeatures;

        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setVariant(String variant) {
            this.variant = variant;
            return this;
        }

        public Builder setFuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public Builder setRegDate(Date regDate) {
            this.regDate = regDate;
            return this;
        }

        public Builder setRegNumber(String regNumber) {
            this.regNumber = regNumber;
            return this;
        }

        public Builder setOdometer(int odometer) {
            this.odometer = odometer;
            return this;
        }

        public Builder setEngineConfiguration(Engine engineConfiguration) {
            this.engineConfiguration = engineConfiguration;
            return this;
        }

        public Builder setCarFeatures(ArrayList<String> carFeatures) {
            this.carFeatures = carFeatures;
            return this;
        }

        public SportsCar buildSportsCar() {
            SportsCar car = new SportsCar();
            ((Car) car).brand = brand;
            ((Car) car).model = model;
            ((Car) car).variant = variant;
            ((Car) car).fuelType = fuelType;
            ((Car) car).regDate = regDate;
            ((Car) car).regNumber = regNumber;
            ((Car) car).odometer = odometer;
            ((Car) car).engineConfiguration = engineConfiguration;
            ((Car) car).carFeatures = carFeatures;

            return car;
        }

        public LuxuryCar buildLuxuryCar() {
            LuxuryCar car = new LuxuryCar();
            ((Car) car).brand = brand;
            ((Car) car).model = model;
            ((Car) car).variant = variant;
            ((Car) car).fuelType = fuelType;
            ((Car) car).regDate = regDate;
            ((Car) car).regNumber = regNumber;
            ((Car) car).odometer = odometer;
            ((Car) car).engineConfiguration = engineConfiguration;
            ((Car) car).carFeatures = carFeatures;

            return car;
        }

        public FamilyCar buildFamilyCar() {
            FamilyCar car = new FamilyCar();
            ((Car) car).brand = brand;
            ((Car) car).model = model;
            ((Car) car).variant = variant;
            ((Car) car).fuelType = fuelType;
            ((Car) car).regDate = regDate;
            ((Car) car).regNumber = regNumber;
            ((Car) car).odometer = odometer;
            ((Car) car).engineConfiguration = engineConfiguration;
            ((Car) car).carFeatures = carFeatures;

            return car;
        }

    }

}
