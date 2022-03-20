package sql;

import java.sql.*;
import java.util.Locale;

public class SqlController {

    private String username;
    private String password;

    public SqlController(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private final String JDBC_URL = "jdbc:mysql://mysql.berggame.nu/car_rental_db";

    public ResultSet performSQLSelect(String sql, SqlParameter... parameters) {
        Connection con = getConnection();
        PreparedStatement s = null;
        try {
            s = con.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                switch (parameters[i].getValue().getClass().getSimpleName().toLowerCase()) {

                    case "integer":
                        s.setInt(i, (int) parameters[i].getValue());
                        break;

                    case "string":
                        s.setString(i, (String) parameters[i].getValue());
                        break;

                    case "double":
                        s.setDouble(i, (Double) parameters[i].getValue());
                        break;

                    case "boolean":
                        s.setBoolean(i, (Boolean) parameters[i].getValue());
                        break;

                    case "date":
                        s.setDate(i, (Date) parameters[i].getValue());
                        break;

                    case "timestamp":
                        s.setTimestamp(i, (Timestamp) parameters[i].getValue());
                        break;

                    default:
                        s.setString(i, parameters[i].getValue().toString());
                        break;
                }
            }
            return s.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void performSQLUpdate(String sql, SqlParameter... parameters) {
        Connection con = getConnection();
        PreparedStatement s = null;
        try {
            s = con.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                switch (parameters[i].getValue().getClass().getSimpleName().toLowerCase()) {

                    case "integer":
                        s.setInt(i, (int) parameters[i].getValue());
                        break;

                    case "string":
                        s.setString(i, (String) parameters[i].getValue());
                        break;

                    case "double":
                        s.setDouble(i, (Double) parameters[i].getValue());
                        break;

                    case "boolean":
                        s.setBoolean(i, (Boolean) parameters[i].getValue());
                        break;

                    case "date":
                        s.setDate(i, (Date) parameters[i].getValue());
                        break;

                    case "timestamp":
                        s.setTimestamp(i, (Timestamp) parameters[i].getValue());
                        break;

                    default:
                        s.setString(i, parameters[i].getValue().toString());
                        break;
                }
            }
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
