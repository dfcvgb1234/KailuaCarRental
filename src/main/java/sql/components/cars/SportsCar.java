package sql.components.cars;

import rest.DMRCar;

public class SportsCar extends Car {

    public SportsCar() {

    }

    public SportsCar(DMRCar car, double pricePerKilometer) {
        super(car, pricePerKilometer);
    }

    @Override
    public String toString() {
        return this.getBrand() + " " + this.getModel() + " " + this.getVariant() + "\n\n" +
                "RegNumber: " + this.getRegNumber() + "\n" +
                "Price pr Kilometer: " + this.getPricePerKilometer();
    }
}
