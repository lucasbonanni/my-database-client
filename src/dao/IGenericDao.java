package dao;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.ArrayList;

public interface IGenericDao {
    DefaultTableModel executeStatement(Connection conn, String query) throws DaoException;

    ArrayList<String> getDatabaseObjects(String catalog, Connection conn) throws DaoException;
}
