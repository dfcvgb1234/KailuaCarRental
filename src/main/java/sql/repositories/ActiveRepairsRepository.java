package sql.repositories;

import sql.SqlRepository;
import sql.SqlController;
import sql.SqlParameter;
import sql.components.Mechanic;
import sql.components.Repair;
import sql.components.cars.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActiveRepairsRepository implements SqlRepository<String, Repair> {

    private final String VIEW_NAME = "view_active_repairs";

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

        String address = repair.getMechanic().getAddress().split(",")[0].trim();
        int zipCode = Integer.parseInt(repair.getMechanic().getAddress().split(",")[1].trim());
        String country = repair.getMechanic().getAddress().split(",")[3].trim();

        controller.performSQLUpdate("CALL sp_ScheduleRepair(?,?,?,?,?,?,?,?,?)",
                new SqlParameter<String>(repair.getMechanic().getName()),
                new SqlParameter<String>(address),
                new SqlParameter<Integer>(zipCode),
                new SqlParameter<String>(country),
                new SqlParameter<String>(repair.getMechanic().getPhoneNumber()),
                new SqlParameter<String>(repair.getCar().getRegNumber()),
                new SqlParameter<String>(repair.getRepairLocation()),
                new SqlParameter<String>(repair.getNote()),
                new SqlParameter<Double>(repair.getCost()));
    }

    @Override
    public void insertAll(List<Repair> repairs) {
        SqlController controller = new SqlController("root", "Admin123");

        for (Repair repair : repairs) {
            String address = repair.getMechanic().getAddress().split(",")[0].trim();
            int zipCode = Integer.parseInt(repair.getMechanic().getAddress().split(",")[1].trim());
            String country = repair.getMechanic().getAddress().split(",")[3].trim();

            controller.performSQLUpdate("CALL sp_ScheduleRepair(?,?,?,?,?,?,?,?,?)",
                    new SqlParameter<String>(repair.getMechanic().getName()),
                    new SqlParameter<String>(address),
                    new SqlParameter<Integer>(zipCode),
                    new SqlParameter<String>(country),
                    new SqlParameter<String>(repair.getMechanic().getPhoneNumber()),
                    new SqlParameter<String>(repair.getCar().getRegNumber()),
                    new SqlParameter<String>(repair.getRepairLocation()),
                    new SqlParameter<String>(repair.getNote()),
                    new SqlParameter<Double>(repair.getCost()));
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
                result.getString("RepairLocation"),
                result.getString("Note"),
                result.getDouble("Cost"),
                mechanic
        );
    }
}
