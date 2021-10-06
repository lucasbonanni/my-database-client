package terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        String query = null;
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
        }


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

    private static String ExecuteStatement(Connection conn, String query){
        StringBuilder builder = new StringBuilder();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
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
    }
}
