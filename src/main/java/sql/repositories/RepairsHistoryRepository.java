package sql.repositories;

import sql.SqlRepository;
import sql.SqlController;
import sql.SqlParameter;
import sql.components.Mechanic;
import sql.components.Repair;
import sql.components.cars.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RepairsHistoryRepository implements SqlRepository<Integer, Repair> {

    private final String VIEW_NAME = "view_repairs_history";
    private final String TABLE_NAME = "repairs";

    @Override
    public Repair findFirstById(Integer id) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " WHERE CarId = ?", new SqlParameter<Integer>(id));

        try {
            if (result != null) {
                if (result.next()) {

                    return getRepairFromResultSet(result);

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
    public List<Repair> getAll() {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME);

        List<Repair> repairs = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    repairs.add(getRepairFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return repairs;
    }

    @Override
    public List<Repair> getAmount(int amount) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " LIMIT = ?", new SqlParameter<Integer>(amount));

        List<Repair> repairs = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    repairs.add(getRepairFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return repairs;
    }

    @Override
    public void insert(Repair repair) {
        SqlController controller = new SqlController("root", "Admin123");

        Timestamp endTime = Timestamp.from(Instant.now());

        controller.performSQLUpdate("UPDATE repairs SET EndTime = ?, Cost = ? WHERE CarId = ?",
                new SqlParameter<Timestamp>(endTime),
                new SqlParameter<Double>(repair.getCost()),
                new SqlParameter<String>(repair.getCar().getRegNumber()));
    }

    @Override
    public void insertAll(List<Repair> repairs) {
        SqlController controller = new SqlController("root", "Admin123");

        Timestamp endTime = Timestamp.from(Instant.now());

        for (Repair repair : repairs) {
            controller.performSQLUpdate("UPDATE repairs SET EndTime = ?, Cost = ? WHERE CarId = ?",
                    new SqlParameter<Timestamp>(endTime),
                    new SqlParameter<Double>(repair.getCost()),
                    new SqlParameter<String>(repair.getCar().getRegNumber()));
        }
    }

    @Override
    public void deleteOnId(Integer id) {
        SqlController controller = new SqlController("root", "Admin123");
        controller.performSQLUpdate("DELETE FROM " + TABLE_NAME + " WHERE CarId = ?", new SqlParameter<Integer>(id));
    }

    private Repair getRepairFromResultSet(ResultSet result) throws SQLException {

        Car car = new CarRepository().findFirstById(result.getString("CarId"));
        Mechanic mechanic = new Mechanic(
                result.getString("FullName"),
                result.getString("Address"),
                result.getString("Phonenumber")
        );

        return new Repair(
                result.getInt("Id"),
                car,
                result.getTimestamp("StartTime"),
                result.getTimestamp("EndTime"),
                result.getString("RepairLocation"),
                result.getString("Note"),
                result.getDouble("Cost"),
                mechanic
        );
    }

    public List<Repair> findAllWithRegistrationNumber(String regNumber) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " WHERE CarId = ?", new SqlParameter<String>(regNumber));

        List<Repair> repairs = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    repairs.add(getRepairFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return repairs;
    }
}
