package sql.repositories;

import sql.SqlController;
import sql.SqlParameter;
import sql.SqlRepository;
import sql.components.Mechanic;
import sql.components.customers.Customer;
import sql.components.customers.DriverLicense;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicRepository implements SqlRepository<String, Mechanic> {

    private final String VIEW_NAME = "view_mechanics";

    @Override
    public Mechanic findFirstById(String id) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " WHERE Phonenumber = ?", new SqlParameter<String>(id));

        try {
            if (result != null) {
                if (result.next()) {

                    return getMechanicFromResultSet(result);

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
    public List<Mechanic> getAll() {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME);

        List<Mechanic> mechanics = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    mechanics.add(getMechanicFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return mechanics;
    }

    @Override
    public List<Mechanic> getAmount(int amount) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " LIMIT ?", new SqlParameter<Integer>(amount));

        List<Mechanic> mechanics = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    mechanics.add(getMechanicFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return mechanics;
    }

    @Override
    public void insert(Mechanic mechanic) {
        SqlController controller = new SqlController("root", "Admin123");

        String address = mechanic.getAddress().split(",")[0].trim();
        String zip = mechanic.getAddress().split(",")[1].trim();
        int zipCode = Integer.parseInt(zip.split(" ")[0].trim());
        String country = mechanic.getAddress().split(",")[2].trim();

        controller.performSQLUpdate("CALL sp_InsertOrUpdateMechanic(?,?,?,?,?)",
                new SqlParameter<String>(mechanic.getName()),
                new SqlParameter<String>(address),
                new SqlParameter<Integer>(zipCode),
                new SqlParameter<String>(country),
                new SqlParameter<String>(mechanic.getPhoneNumber()));
    }

    @Override
    public void insertAll(List<Mechanic> mechanics) {

        SqlController controller = new SqlController("root", "Admin123");
        for (Mechanic mechanic : mechanics) {
            String address = mechanic.getAddress().split(",")[0].trim();
            String zip = mechanic.getAddress().split(",")[1].trim();
            int zipCode = Integer.parseInt(zip.split(" ")[0].trim());
            String country = mechanic.getAddress().split(",")[2].trim();

            controller.performSQLUpdate("CALL sp_InsertOrUpdateMechanic(?,?,?,?,?)",
                    new SqlParameter<String>(mechanic.getName()),
                    new SqlParameter<String>(address),
                    new SqlParameter<Integer>(zipCode),
                    new SqlParameter<String>(country),
                    new SqlParameter<String>(mechanic.getPhoneNumber()));
        }
    }

    @Override
    public void deleteOnId(String id) {
        SqlController controller = new SqlController("root", "Admin123");
        controller.performSQLUpdate("DELETE FROM mechanics WHERE Phonenumber = ?", new SqlParameter<String>(id));
    }

    private Mechanic getMechanicFromResultSet(ResultSet result) throws SQLException {

        return new Mechanic(result.getString("FullName"), result.getString("Address"), result.getString("Phonenumber"));

    }
}
