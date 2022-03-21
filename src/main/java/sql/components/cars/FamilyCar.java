package sql.components.cars;

import rest.DMRCar;

public class FamilyCar extends Car{

    public FamilyCar() {

    }

    public FamilyCar(DMRCar car, double pricePerKilometer) {
        super(car, pricePerKilometer);
    }

    @Override
    public String toString() {
        return this.getBrand() + " " + this.getModel() + " " + this.getVariant() + "\n\n" +
                "RegNumber: " + this.getRegNumber() + "\n" +
                "Price pr Kilometer: " + this.getPricePerKilometer();
    }
}
