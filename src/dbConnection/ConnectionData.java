package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionData {
    private String host;
    private String port;
    private String databaseName;
    private IDBDriver driver;
    private Properties properties;

    public String getetUrl(){
        return String.format("jdbc:%s://%s:%s/%s",driver.getDatabaseType(),host,port,databaseName);
    }

    public void setProperties(Properties properties){
        this.properties = properties;
    }

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
}
