package sql.repositories;

import sql.SqlRepository;
import sql.SqlController;
import sql.SqlParameter;
import sql.components.RentalContract;
import sql.components.cars.Car;
import sql.components.customers.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CompletedRentalContractsRepository implements SqlRepository<Integer, RentalContract> {

    private final String TABLE_NAME = "completed_rental_contracts";

    @Override
    public RentalContract findFirstById(Integer id) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + TABLE_NAME + " WHERE Id = ?", new SqlParameter<Integer>(id));

        try {
            if (result != null) {
                if (result.next()) {

                    return getRentalContractFromResultSet(result);

                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<RentalContract> getAll() {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + TABLE_NAME);

        List<RentalContract> contracts = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    contracts.add(getRentalContractFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }

    public List<RentalContract> getAllWithCarId(String carId) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + TABLE_NAME + " WHERE CarId = ?", new SqlParameter<String>(carId));

        List<RentalContract> contracts = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    contracts.add(getRentalContractFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }

    public List<RentalContract> getAllOnSpecificDate(Timestamp date) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM car_rental_db.completed_rental_contracts WHERE StartTime BETWEEN ? AND DATE_ADD(?, INTERVAL 1 DAY)",
                new SqlParameter<Timestamp>(date),
                new SqlParameter<Timestamp>(date));

        List<RentalContract> contracts = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    contracts.add(getRentalContractFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }

    @Override
    public List<RentalContract> getAmount(int amount) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + TABLE_NAME + " LIMIT ?", new SqlParameter<Integer>(amount));

        List<RentalContract> contracts = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    contracts.add(getRentalContractFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }

    @Override
    public void insert(RentalContract contract) {
        SqlController controller = new SqlController("root", "Admin123");
        controller.performSQLUpdate("CALL sp_CompleteRental(?,?,?)",
                new SqlParameter<String>(contract.getCar().getRegNumber()),
                new SqlParameter<Integer>(contract.getKilometers()),
                new SqlParameter<Double>(contract.getPrice()));
    }

    @Override
    public void insertAll(List<RentalContract> contracts) {
        SqlController controller = new SqlController("root", "Admin123");

        for (RentalContract contract : contracts) {
            controller.performSQLUpdate("CALL sp_CompleteRental(?,?,?)",
                    new SqlParameter<String>(contract.getCar().getRegNumber()),
                    new SqlParameter<Integer>(contract.getKilometers()),
                    new SqlParameter<Double>(contract.getPrice()));
        }
    }

    @Override
    public void deleteOnId(Integer id) {
        SqlController controller = new SqlController("root", "Admin123");
        controller.performSQLUpdate("DELETE FROM " + TABLE_NAME + " WHERE Id = ?", new SqlParameter<Integer>(id));
    }

    private RentalContract getRentalContractFromResultSet(ResultSet result) throws SQLException {

        Car rentalCar = new CarRepository().findFirstById(result.getString("CarId"));
        Customer customer = new CustomerRepository().findFirstById(result.getInt("CustomerId"));

        return new RentalContract(
                result.getInt("Id"),
                rentalCar,
                customer,
                result.getTimestamp("StartTime"),
                result.getTimestamp("EndTime"),
                result.getInt("DrivenRange"),
                result.getDouble("Price")
        );
    }
}
