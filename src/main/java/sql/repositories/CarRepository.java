package sql.repositories;

import sql.SqlComponent;
import sql.SqlController;
import sql.SqlParameter;
import sql.components.cars.Car;
import sql.components.cars.Engine;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarRepository implements SqlComponent<String, Car> {

    private final String VIEW_NAME = "view_cars";

    @Override
    public Car findFirstById(String id) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " WHERE RegistrationNumber = ?", new SqlParameter<String>(id));

        try {
            if (result != null) {
                if (result.next()) {

                    return getCarFromResultSet(result);

                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Last resort
        return null;
    }

    @Override
    public List<Car> getAll() {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME);

        List<Car> cars = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    cars.add(getCarFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @Override
    public List<Car> getAmount(int amount) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " LIMIT = ?", new SqlParameter<Integer>(amount));

        List<Car> cars = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    cars.add(getCarFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @Override
    public void insert(Car car) {
        SqlController controller = new SqlController("root", "Admin123");
        controller.performSQLUpdate("CALL sp_InsertOrUpdateCar(?,?,?,?,?,?,?,?,?,?)",
                new SqlParameter<String>(car.getRegNumber()),
                new SqlParameter<String>(getCarType(car)),
                new SqlParameter<String>(car.getBrand()),
                new SqlParameter<String>(car.getModel()),
                new SqlParameter<String>(car.getVariant()),
                new SqlParameter<Integer>(car.getEngineConfiguration().getEngineSize()),
                new SqlParameter<Integer>(car.getEngineConfiguration().getEnginePower()),
                new SqlParameter<Date>(car.getRegDate()),
                new SqlParameter<Integer>(car.getOdometer()),
                new SqlParameter<String>(car.getSerializedCarFeatures()));
    }

    @Override
    public void insertAll(List<Car> cars) {
        SqlController controller = new SqlController("root", "Admin123");
        for (Car car : cars) {
            controller.performSQLUpdate("CALL sp_InsertOrUpdateCar(?,?,?,?,?,?,?,?,?,?)",
                    new SqlParameter<String>(car.getRegNumber()),
                    new SqlParameter<String>(getCarType(car)),
                    new SqlParameter<String>(car.getBrand()),
                    new SqlParameter<String>(car.getModel()),
                    new SqlParameter<String>(car.getVariant()),
                    new SqlParameter<Integer>(car.getEngineConfiguration().getEngineSize()),
                    new SqlParameter<Integer>(car.getEngineConfiguration().getEnginePower()),
                    new SqlParameter<Date>(car.getRegDate()),
                    new SqlParameter<Integer>(car.getOdometer()),
                    new SqlParameter<String>(car.getSerializedCarFeatures()));
        }
    }

    private String getCarType (Car car) {
        switch (car.getClass().getSimpleName()) {

            case "FamilyCar":
                return "Family Car";

            case "LuxuryCar":
                return "Luxury Car";

            case "SportsCar":
                return "Sports Car";

            default :
                return "";
        }
    }


    private Car getCarFromResultSet(ResultSet result) throws SQLException {

        Car.Builder carBuilder = new Car.Builder();

        carBuilder.setRegistrationNumber(result.getString("RegistrationNumber"));
        carBuilder.setBrand(result.getString("Brand"));
        carBuilder.setModel(result.getString("Model"));
        carBuilder.setVariant(result.getString("Variant"));

        String engineSpecs = result.getString("EngineSpecs");
        int engineSize = Integer.parseInt(engineSpecs.split(",")[0].trim());
        int enginePower = Integer.parseInt(engineSpecs.split(",")[1].trim());
        Engine carEngine = new Engine(engineSize, enginePower);

        carBuilder.setEngineConfiguration(carEngine);
        carBuilder.setRegDate(result.getDate("RegistrationDate"));
        carBuilder.setOdometer(result.getInt("Odometer"));
        carBuilder.setCarFeatures(new ArrayList<String>(List.of(result.getString("CarFeatures").split(","))));

        switch (result.getString("CarType").toLowerCase()) {

            case "sports car":
                return carBuilder.buildSportsCar();

            case "luxury car":
                return carBuilder.buildLuxuryCar();

            case "family car":
                return carBuilder.buildFamilyCar();

            default:
                return null;
        }
    }
}
