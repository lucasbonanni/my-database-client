package connection;

import exceptions.DaoException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionData implements Serializable {
    private static final long serialVersionUID = 4936929128525393354L;
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




    /**
     * Se debería mover el connection manager
     */
    public void Connect() throws DaoException {
        connection = null;
        try {
            connection = DriverManager.getConnection(this.getUrl(), this.userName,this.password);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
    public Connection getConnection() throws DaoException {
        if(this.connection == null){
            throw new DaoException("No se estableción una conexión");
        }
        return this.connection;
    }

    public void disconnect() throws DaoException {
        if(this.connection != null){
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new DaoException(e.getMessage(),e.getErrorCode(),e);
            }
        }
        this.connection = null;
    }


    @Override
    public String toString() {
        return String.format("%s:%s/%s",host,port,databaseName);
    }

    public String getUrl(){
        return String.format("jdbc:%s://%s:%s/%s","mysql",host,port,databaseName);
    }

    public boolean isConnected(){
        boolean status = false;
        try {
            status = this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
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



}
