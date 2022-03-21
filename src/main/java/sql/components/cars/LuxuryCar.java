package sql.components.cars;

import rest.DMRCar;

public class LuxuryCar extends Car{

    public LuxuryCar() {

    }

    public LuxuryCar(DMRCar car, double pricePerKilometer) {
        super(car, pricePerKilometer);
    }

    @Override
    public String toString() {
        return this.getBrand() + " " + this.getModel() + " " + this.getVariant() + "\n\n" +
                "RegNumber: " + this.getRegNumber() + "\n" +
                "Price pr Kilometer: " + this.getPricePerKilometer();
    }
}
