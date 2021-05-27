package StartUp;

import java.sql.*;

public class Database {
    private static Database instance = null;
    private static final String DataBase_URL = "jdbc:mysql://localhost:3306/proiect_pao";
    private static final String User = "root";
    private static final String Password = "";
    Connection connection = null;

    private Database() throws SQLException {
        connection = DriverManager.getConnection(DataBase_URL,User,Password);
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public ResultSet query(String query) throws SQLException {
        Statement statement = null;
        statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void update(String query) throws SQLException
    {
        Statement statement = null;
        statement = connection.createStatement();
        statement.executeUpdate(query);
    }


}
