package sql.components.cars;

public class Engine {

    private int engineSize;
    private int enginePower;
    private String fuelType;

    public int getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(int engineSize) {
        this.engineSize = engineSize;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Engine(int engineSize, int enginePower, String fuelType) {
        this.engineSize = engineSize;
        this.enginePower = enginePower;
        this.fuelType = fuelType;
    }
}
