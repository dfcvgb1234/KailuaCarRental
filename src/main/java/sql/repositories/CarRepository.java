package sql.repositories;

import sql.SqlRepository;
import sql.SqlController;
import sql.SqlParameter;
import sql.components.cars.Car;
import sql.components.cars.Engine;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements SqlRepository<String, Car> {

    private final String VIEW_NAME = "view_cars";
    private final String VIEW_NAME_AVAILABLE = "view_available_cars";
    private final String TABLE_NAME = "cars";

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

    public Car findFirstAvailableById(String id) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME_AVAILABLE + " WHERE RegistrationNumber = ?", new SqlParameter<String>(id));

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

    public List<Car> getAllCarsOfType(String carType) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " WHERE CarType = ?", new SqlParameter<String>(carType));

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

    public List<Car> getAllAvailableCarsOfType(String carType) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME_AVAILABLE + " WHERE CarType = ?", new SqlParameter<String>(carType));

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

    public List<Car> getAllAvailableCars() {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME_AVAILABLE);

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
        controller.performSQLUpdate("CALL sp_InsertOrUpdateCar(?,?,?,?,?,?,?,?,?,?,?,?)",
                new SqlParameter<String>(car.getRegNumber()), // AK49577
                new SqlParameter<String>(getCarType(car)),  // Luxury Car
                new SqlParameter<Double>(car.getPricePerKilometer()), // 5.8
                new SqlParameter<String>(car.getBrand()), // Audi
                new SqlParameter<String>(car.getModel()), // A3
                new SqlParameter<String>(car.getVariant()), // TSI
                new SqlParameter<Integer>(car.getEngineConfiguration().getEngineSize()), // 2000
                new SqlParameter<Integer>(car.getEngineConfiguration().getEnginePower()), // 170
                new SqlParameter<String>(car.getEngineConfiguration().getFuelType()), // Diesel
                new SqlParameter<Date>(car.getRegDate()), // STR_TO_DATE('03-05-2019', '%d-%m-%Y')
                new SqlParameter<Integer>(car.getOdometer()), // 25000
                new SqlParameter<String>(car.getSerializedCarFeatures())); // Leather seats, heated steering wheel
    }

    @Override
    public void insertAll(List<Car> cars) {
        SqlController controller = new SqlController("root", "Admin123");
        for (Car car : cars) {
            controller.performSQLUpdate("CALL sp_InsertOrUpdateCar(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    new SqlParameter<String>(car.getRegNumber()),
                    new SqlParameter<String>(getCarType(car)),
                    new SqlParameter<Double>(car.getPricePerKilometer()),
                    new SqlParameter<String>(car.getBrand()),
                    new SqlParameter<String>(car.getModel()),
                    new SqlParameter<String>(car.getVariant()),
                    new SqlParameter<Integer>(car.getEngineConfiguration().getEngineSize()),
                    new SqlParameter<Integer>(car.getEngineConfiguration().getEnginePower()),
                    new SqlParameter<String>(car.getEngineConfiguration().getFuelType()),
                    new SqlParameter<Date>(car.getRegDate()),
                    new SqlParameter<Integer>(car.getOdometer()),
                    new SqlParameter<String>(car.getSerializedCarFeatures()));
        }
    }

    @Override
    public void deleteOnId(String id) {
        SqlController controller = new SqlController("root", "Admin123");
        controller.performSQLUpdate("UPDATE " + TABLE_NAME +" SET StopCode = true WHERE RegistrationNumber = ?", new SqlParameter<String>(id));
    }

    private String getCarType (Car car) {
        return switch (car.getClass().getSimpleName()) {
            case "FamilyCar" -> "Family Car";
            case "LuxuryCar" -> "Luxury Car";
            case "SportsCar" -> "Sports Car";
            default -> "";
        };
    }


    private Car getCarFromResultSet(ResultSet result) throws SQLException {

        Car.Builder carBuilder = new Car.Builder();

        carBuilder.setRegNumber(result.getString("RegistrationNumber"))
                .setBrand(result.getString("Brand"))
                .setModel(result.getString("Model"))
                .setVariant(result.getString("Variant"))
                .setPricePerKilometer(result.getDouble("PricePerKilometer"));

        String engineSpecs = result.getString("EngineSpecs");
        int engineSize = Integer.parseInt(engineSpecs.split(",")[0].trim());
        int enginePower = Integer.parseInt(engineSpecs.split(",")[1].trim());
        String fuelType = engineSpecs.split(",")[2].trim();
        Engine carEngine = new Engine(engineSize, enginePower, fuelType);

        carBuilder.setEngineConfiguration(carEngine)
                .setRegDate(result.getDate("RegistrationDate"))
                .setOdometer(result.getInt("Odometer"))
                .setCarFeatures(new ArrayList<String>(List.of(result.getString("CarFeatures").split(","))));

        return switch (result.getString("CarType").toLowerCase()) {
            case "sports car" -> carBuilder.buildSportsCar();
            case "luxury car" -> carBuilder.buildLuxuryCar();
            case "family car" -> carBuilder.buildFamilyCar();
            default -> null;
        };
    }


}
