package servis;

import java.sql.*;
import java.util.Properties;

public class Connect {
    private static Connection connection = null;

    public static void connect() throws SQLException {
        String url = "jdbc:mysql://localhost/pixpal";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        try {
            connection = DriverManager.getConnection(url, properties);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    public static void disconnect() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
                connection = null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    public static void executeDML(String sql) throws SQLException {
        Statement statement = null;
        try {
            if (connection == null)
                throw new SQLException();
            statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
        finally {
            if (!statement.isClosed())
                statement.close();
        }
    }

    public static ResultSet getData(String sql) throws SQLException {
        connect();
        Statement statement;
        ResultSet resultSet;

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        return resultSet;
    }
}

