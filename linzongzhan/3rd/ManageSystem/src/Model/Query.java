package Model;

import java.sql.*;

/**
 * Created by linzongzhan on 2017/7/15.
 */
public class Query {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/manage_system";

    static final String USER = "root";
    static final String PASSWORD = "88888888";

    public static Statement query () {

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);
           // connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            connection = DriverManager.getConnection("jdbc:mysql://localhost/manage_system?useUnicode=true&characterEncoding=utf-8&useSSL=false",USER,PASSWORD);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }
}
