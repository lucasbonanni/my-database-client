package terminal;

import dbConnection.ExecuteStatamentEvent;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class Main {
    private EventListenerList listenerList = new EventListenerList();

    public static void main(String[] args) {
        /*String query = null;
        //Inicializar conexión.
        Connection conn = initializeConnection();
        try {
            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine

            query = reader.readLine();

            ExecuteStatement(conn,query);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static Connection initializeConnection()
    {
        String connString = "jdbc:mysql://localhost:3306/sakila";
        Properties prop = new Properties();
        prop.put("user", "lucas");
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
/*
    private static String ExecuteStatement(Connection conn, String query){
        StringBuilder builder = new StringBuilder();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query); /* Sirver para cualquier query, update, delete, etc.view
            var r = rs.getMetaData();
            r.getColumnCount();
            String columns = "";
            for(int i = 1; i <= r.getColumnCount(); i++) {
                columns +=  r.getColumnName(i)+ ", ";
            }
            System.out.println(columns);
            while (rs.next()) {
                String line = "";
                for(int i = 1; i <= r.getColumnCount(); i++) {
                    line +=  rs.getString(i)+ ", ";
                }
                System.out.println(line);
            }

            rs.close();
            st.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }*/

    public void ExcecuteStatement2(Connection conn, String query){
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
            if (listeners[i]==ActionListener.class) {
                 e = new ExecuteStatamentEvent(rs,this,
                            ActionEvent.ACTION_PERFORMED,
                            "actionCommand");

                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }




    public void ExecuteQuery(String query){
        //Inicializar conexión.
        Connection conn = initializeConnection();
        ExcecuteStatement2(conn,query);
    }
}
