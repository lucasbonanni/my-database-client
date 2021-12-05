package dbConnection;

import exceptions.ConnectionException;
import exceptions.DaoException;
import exceptions.ServiceException;

import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class GenericService {

    private final ConnectionManager connectionManager;
    private GenericDao genericDao;
    private EventListenerList listenerList = new EventListenerList();

    public GenericService() {
         connectionManager = ConnectionManager.getInstance();
         genericDao = new GenericDao();
    }

    public void addListener(ActionListener actionListener) {
        this.listenerList.add(ActionListener.class,actionListener);
    }

    public void executeStatement(String query) throws ConnectionException {
        DefaultTableModel tableModel = null;
        try {
            tableModel = this.genericDao.executeStatement(connectionManager.getConnection(), query);

        } catch (DaoException e) {
            /*
            Capturo la excepción del dao y en este caso muestro el error en la parte inferior de la pantalla
            como lo se muestran en los clientes sql
            */
            tableModel = buildErrorTable(e);
        }
        catch (ConnectionException ex){
            throw ex;
        }
        fireActionPerformed(tableModel);
    }

    private DefaultTableModel buildErrorTable(DaoException e) {
        Vector<String> columnNames = new Vector<String>();
        Vector<Vector<Object>> data = new Vector<>();
        columnNames.add("Mensaje");
        columnNames.add("Codigo de error");
        Vector<Object> row = new Vector<>();
        row.add(e.getMessage());
        row.add(e.getErrorCode());
        data.add(row);

        return new DefaultTableModel(data, columnNames);
    }



    public ArrayList<String> getDatabaseObjects(String catalog) throws ServiceException, ConnectionException {
        ArrayList<String> results = new ArrayList<>();
        try {
            results =  this.genericDao.getDatabaseObjects(catalog, this.connectionManager.getConnection());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e.getErrorCode(),e.getCause());
        }
        catch (ConnectionException ex){
            throw ex;
        }
        return results;
    }

    protected void fireActionPerformed(DefaultTableModel tableModel) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ExecuteStatementEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]== ActionListener.class) {
                e = new ExecuteStatementEvent(tableModel,this,
                        ActionEvent.ACTION_PERFORMED,
                        "actionCommand");
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }
}
