package dbConnection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionData implements Serializable {
    //region Atributos
    private String driverName;
    private String host;
    private int port;
    private String databaseName;
    private String userName;
    private String password;


    Connection connection;



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
        return String.format("jdbc:%s://%s:%s/%s","mysql",host,port,databaseName);
    }


    /**
     * Se debería mover el connection manager
     */
    public void Connect(){
        connection = null;
        try {
            connection = DriverManager.getConnection(this.getetUrl(), this.userName,this.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public Connection getConnection() {
        return this.connection;
    }

    public void disconnect() {
        if(this.connection != null){
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.connection = null;
    }
    //endregion



}
