package dbConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GenericDao
{
    public DefaultTableModel executeStatement(Connection conn, String query){
        DefaultTableModel tableModel = null;
        try {
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query); /* Sirver para cualquier query, update, delete, etc.view */
            tableModel = buildTableModel(rs);
            rs.close();
            st.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableModel;
    }

    public List<String> getDatabaseObjects(Connection conn){
        DatabaseMetaData cdmd = null;
        List<String> results = new ArrayList<String>();
        try {
            cdmd = conn.getMetaData();
            ResultSet tables = cdmd.getTables(null, null, "%", new String[]{"TABLE", "VIEW", "INDEX", "SYSTEM TABLE", "information_schema"});
            while (tables.next()) {
                results.add(tables.getString("TABLE_TYPE") + "." + tables.getString("TABLE_SCHEM") + "." + tables.getString("TABLE_NAME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public ArrayList<String> getDatabaseObjects(String catalog, Connection conn){
        ArrayList<String> results = new ArrayList<String>();
        try {
            DatabaseMetaData cdmd = conn.getMetaData();

            //"INDEX" "ATTR_TYPE_NAME" "ATTR_TYPE_NAME","TYPE_NAME","ATTR_NAME"
            //ResultSet tables = cdmd.getTables(null,null,"%",new String[]{ "TABLE", "VIEW" });
            ResultSet tables = cdmd.getTables("world",null,"%",new String[]{ "TABLE", "VIEW" });
            while (tables.next()) {
                results.add(tables.getString("TABLE_TYPE") + "." + tables.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return results;
    }




    private static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // Obtengo las columnas
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // Obtengo las filas
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
}
