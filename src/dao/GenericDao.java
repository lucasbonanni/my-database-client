package dao;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class GenericDao implements IGenericDao {
    @Override
    public DefaultTableModel executeStatement(Connection conn, String query) throws DaoException {
        DefaultTableModel tableModel = null;
        try {
            Statement st = conn.createStatement();

            st.execute(query); /* Sirver para cualquier query, update, delete, etc.view */
            ResultSet rs = st.getResultSet();
            tableModel = buildTableModel(rs);
            if (rs != null) {
                rs.close();
            }
            st.close();


        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e.getErrorCode(),e.getCause());
        }
        return tableModel;
    }




    @Override
    public ArrayList<String> getDatabaseObjects(String catalog, Connection conn) throws DaoException {
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
            throw new DaoException(e.getMessage(),e.getErrorCode(),e.getCause());
        }
            return results;
    }




    private static DefaultTableModel buildTableModel(ResultSet rs)  throws DaoException {
        Vector<String> columnNames = new Vector<String>();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        try {
            if (rs != null) {
                ResultSetMetaData metaData = rs.getMetaData();
                // Obtengo las columnas
                int columnCount = metaData.getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                // Obtengo las filas
                while (rs.next()) {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rs.getObject(columnIndex));
                    }
                    data.add(vector);
                }
            } else {
                columnNames.add("Resultado");
                Vector<Object> vector = new Vector<Object>();
                vector.add("Se ejecutó correctamente");
                data.add(vector);
            }
        }
        catch (SQLException e ){
            throw new DaoException(e.getMessage(),e.getErrorCode(),e.getCause());
        }

        return new DefaultTableModel(data, columnNames);
    }
}
