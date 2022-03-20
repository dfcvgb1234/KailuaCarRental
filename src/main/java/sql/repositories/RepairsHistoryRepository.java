package sql.repositories;

import sql.SqlComponent;
import sql.SqlController;
import sql.SqlParameter;
import sql.components.Mechanic;
import sql.components.Repair;
import sql.components.cars.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairsHistoryRepository implements SqlComponent<String, Repair> {

    private final String VIEW_NAME = "view_repairs_history";

    @Override
    public Repair findFirstById(String id) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " WHERE CarId = ?", new SqlParameter<String>(id));

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
        controller.performSQLUpdate("UPDATE repairs SET EndTime=CURRENT_TIMESTAMP()");
    }

    @Override
    public void insertAll(List<Repair> repairs) {
        SqlController controller = new SqlController("root", "Admin123");
        for (Repair repair : repairs) {
            controller.performSQLUpdate("UPDATE repairs SET EndTime=CURRENT_TIMESTAMP()");
        }
    }

    private Repair getRepairFromResultSet(ResultSet result) throws SQLException {

        Car car = new CarRepository().findFirstById(result.getString("CarId"));
        Mechanic mechanic = new Mechanic(
                result.getString("FullName"),
                result.getString("Address"),
                result.getString("Phonenumber")
        );

        return new Repair(
                car,
                result.getTimestamp("StartTime"),
                result.getTimestamp("EndTime"),
                result.getString("RepairLocation"),
                result.getString("Note"),
                mechanic
        );
    }
}
