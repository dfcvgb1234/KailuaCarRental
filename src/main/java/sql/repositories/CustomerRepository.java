package sql.repositories;

import sql.SqlRepository;
import sql.SqlController;
import sql.SqlParameter;
import sql.components.customers.Customer;
import sql.components.customers.DriverLicense;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements SqlRepository<Integer, Customer> {

    private final String VIEW_NAME = "view_customers";
    private final String TABLE_NAME = "customers";

    @Override
    public Customer findFirstById(Integer id) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " WHERE Id = ?", new SqlParameter<Integer>(id));

        try {
            if (result != null) {
                if (result.next()) {

                    return getCustomerFromResultSet(result);

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
    public List<Customer> getAll() {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME);

        List<Customer> customers = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    customers.add(getCustomerFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public List<Customer> getAmount(int amount) {
        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " LIMIT = ?", new SqlParameter<Integer>(amount));

        List<Customer> customers = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    customers.add(getCustomerFromResultSet(result));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public void insert(Customer customer) {
        SqlController controller = new SqlController("root", "Admin123");

        String address = customer.getAddress().split(",")[0].trim();
        String zip = customer.getAddress().split(",")[1].trim();
        int zipCode = Integer.parseInt(zip.split(" ")[0].trim());
        String country = customer.getAddress().split(",")[2].trim();

        DriverLicense license = customer.getLicense();

        controller.performSQLUpdate("CALL sp_InsertOrUpdateCustomer(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                new SqlParameter<String>(customer.getName()),
                new SqlParameter<String>(address),
                new SqlParameter<Integer>(zipCode),
                new SqlParameter<String>(country),
                new SqlParameter<String>(customer.getMobilePhone()),
                new SqlParameter<String>(customer.getPhone()),
                new SqlParameter<String>(customer.getEmail()),
                new SqlParameter<Integer>(license.getLicenseNumber()),
                new SqlParameter<String>(license.getLicenseName()),
                new SqlParameter<Date>(license.getStartDate()),
                new SqlParameter<Date>(license.getExpirationDate()),
                new SqlParameter<Date>(license.getBirthday()),
                new SqlParameter<String>(license.getCountryOfOrigin()));
    }

    @Override
    public void insertAll(List<Customer> customers) {
        SqlController controller = new SqlController("root", "Admin123");
        for (Customer customer : customers) {
            String address = customer.getAddress().split(",")[0].trim();
            int zipCode = Integer.parseInt(customer.getAddress().split(",")[1].trim());
            String country = customer.getAddress().split(",")[3].trim();

            DriverLicense license = customer.getLicense();

            controller.performSQLUpdate("CALL sp_InsertOrUpdateCustomer(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    new SqlParameter<String>(customer.getName()),
                    new SqlParameter<String>(address),
                    new SqlParameter<Integer>(zipCode),
                    new SqlParameter<String>(country),
                    new SqlParameter<String>(customer.getMobilePhone()),
                    new SqlParameter<String>(customer.getPhone()),
                    new SqlParameter<String>(customer.getEmail()),
                    new SqlParameter<Integer>(license.getLicenseNumber()),
                    new SqlParameter<String>(license.getLicenseName()),
                    new SqlParameter<Date>(license.getStartDate()),
                    new SqlParameter<Date>(license.getExpirationDate()),
                    new SqlParameter<Date>(license.getBirthday()),
                    new SqlParameter<String>(license.getCountryOfOrigin()));
        }
    }

    @Override
    public void deleteOnId(Integer id) {
        SqlController controller = new SqlController("root", "Admin123");
        controller.performSQLUpdate("DELETE FROM " + TABLE_NAME + " WHERE Id = ?", new SqlParameter<Integer>(id));
    }

    public Customer findFirstByPhonenumber(String phonenumber) {

        if (phonenumber == "") {
            return null;
        }

        SqlController controller = new SqlController("root", "Admin123");
        ResultSet result = controller.performSQLSelect("SELECT * FROM " + VIEW_NAME + " WHERE MobilePhone = ? OR Phone = ?",
                new SqlParameter<String>(phonenumber),
                new SqlParameter<String>(phonenumber));

        try {
            if (result != null) {
                if (result.next()) {
                    return getCustomerFromResultSet(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Customer getCustomerFromResultSet(ResultSet result) throws SQLException {
        DriverLicense license = new DriverLicense(
                result.getInt("LicenseNumber"),
                result.getString("LicenseName"),
                result.getDate("StartDate"),
                result.getDate("ExpirationDate"),
                result.getDate("Birthday"),
                result.getString("CountryOfOrigin")
        );

        return new Customer(
                result.getInt("Id"),
                result.getString("FullName"),
                result.getString("Address"),
                result.getString("Mobilephone"),
                result.getString("Phone"),
                result.getString("Email"),
                license
        );
    }
}
