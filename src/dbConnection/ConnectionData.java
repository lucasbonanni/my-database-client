package dbConnection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionData implements Serializable {
    //region Atributos
    private String driverName;
    private String host;
    private int port;
    private String databaseName;
    private String userName;
    private String password;
    //endregion
    private IDBDriver driver;
    private Properties properties;



    //region Constructores
    public ConnectionData() {
    }

    public ConnectionData(String driverName, String host, int port, String databaseName, String userName, String password) {
        this.driverName = driverName;
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
    }
    //endregion


    public String getetUrl(){
        return String.format("jdbc:%s://%s:%s/%s",driver.getDatabaseType(),host,port,databaseName);
    }


    /**
     * Se debería mover el connection manager
     * @return Connection
     */
    public Connection getConnection(){
        this.driver.registerDriver();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.getetUrl(), this.properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection initializeConnection()
    {
        String connString = "jdbc:mysql://127.0.0.1:3306/world";
        Properties prop = new Properties();
        prop.put("user", "usuario");
        prop.put("password", "password");
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connString, prop);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }









    @Override
    public String toString() {
        return String.format("%s:%s/%s",host,port,databaseName);
    }

    //region Getters

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUser() {
        return this.userName;
    }

    public String getDriverName() {
        return this.driverName;
    }
    //endregion

    //region Setters
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //endregion

    public void setProperties(Properties properties){
        this.properties = properties;
    }


}
