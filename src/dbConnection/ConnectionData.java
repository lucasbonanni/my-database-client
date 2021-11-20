package dbConnection;

import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.*;
import java.util.Properties;

public class ConnectionData implements Serializable {
    private String driverName;
    private String host;
    private int port;
    private String databaseName;
    private String userName;
    private String password;
    private IDBDriver driver;
    private Properties properties;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

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

    private EventListenerList listenerList = new EventListenerList();

    public void addListener(ActionListener actionListener) {
        this.listenerList.add(ActionListener.class,actionListener);
    }

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

    private static Connection initializeConnection()
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

    public void excecuteStatement(Connection conn, String query){
        StringBuilder builder = new StringBuilder();
        try {
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query); /* Sirver para cualquier query, update, delete, etc.view */
            this.fireActionPerformed(rs);
            rs.close();
            st.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void fireActionPerformed(ResultSet rs) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ExecuteStatamentEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]== ActionListener.class) {
                e = new ExecuteStatamentEvent(rs,this,
                        ActionEvent.ACTION_PERFORMED,
                        "actionCommand");
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

    public void executeQuery(String query){
        //Inicializar conexión.
        Connection conn = initializeConnection();
        excecuteStatement(conn,query);
    }

    @Override
    public String toString() {
        return String.format("%s:%s/%s",host,port,databaseName);
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
}
