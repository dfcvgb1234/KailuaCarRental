import cars.Car;
import cars.SportsCar;
import rest.DMRCar;
import rest.HttpHelper;

public class Main {

    public static void main(String[] args) {
        new HttpHelper().getDMRCar("AK49576");
        SportsCar car = new Car.Builder().buildSportsCar();
    }
}
