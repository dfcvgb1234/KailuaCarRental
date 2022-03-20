package sql.components.cars;

public class Engine {

    private int engineSize;
    private int enginePower;

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

    public Engine(int engineSize, int enginePower) {
        this.engineSize = engineSize;
        this.enginePower = enginePower;
    }
}
