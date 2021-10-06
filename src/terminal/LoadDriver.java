package terminal;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Properties;
public class LoadDriver {

    public static void main(String[] args) {

        File jarFile = new File("C:/temp/postgresql-42.2.24.jar");

        var exist = jarFile.exists();
        URL jarUrl = null;
        try {
            jarUrl = jarFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLClassLoader loader = new URLClassLoader(new URL[]{jarUrl}, ClassLoader.getSystemClassLoader());
        Driver driver = null;
        try {
            driver = (Driver) loader.loadClass("org.postgresql.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();
        prop.put("user", "postgres");
        prop.put("password", "password");
        try {
            Connection conn = driver.connect("jdbc:postgresql://localhost:5432/bm_ar_tax_company00", prop);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT code, description FROM public.activity limit 5");
            while (rs.next())
            {
                //System.out.print("Column 1 returned ");
                System.out.println("code: " + rs.getString(1) + "  Description: "+ rs.getString(2));
            }
            rs.close();
            st.close();
            DatabaseMetaData cdmd = conn.getMetaData();
            ResultSet tables = cdmd.getTables(null,null,"%",new String[]{ "TABLE", "VIEW", "INDEX", "SYSTEM TABLE","information_schema" });
            while(tables.next()) {
                System.out.println(tables.getString("TABLE_TYPE") + " " + tables.getString("TABLE_SCHEM") + " " + tables.getString("TABLE_NAME"));
            }


            conn.close();

            //1. Create the frame.
            JFrame frame = new JFrame("FrameDemo");
            frame.setSize(800,600);

            //2. Optional: What happens when the frame closes?
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //3. Create components and put them in the frame.
            //...create emptyLabel...
            var emptyLabel = new JLabel();
            emptyLabel.setText("prueba de label");

            frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
            //4. Size the frame.
            frame.pack();

            //5. Show it.
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
