package sql;

import java.sql.*;

public class SqlController {

    private String username;
    private String password;

    public SqlController(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/car_rental_db";

    public ResultSet performSQLSelect(String sql, SqlParameter... parameters) {
        Connection con = getConnection();
        PreparedStatement s = null;
        try {
            s = con.prepareStatement(sql);
            for (SqlParameter parameter : parameters) {
                System.out.println(parameter.getType().getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void performSQLUpdate(String sql) {

    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
