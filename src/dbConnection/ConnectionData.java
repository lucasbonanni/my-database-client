package dbConnection;

import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

public class ConnectionData {
    private String host;
    private String port;
    private String databaseName;
    private IDBDriver driver;
    private Properties properties;

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
}
