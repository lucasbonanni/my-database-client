package dbConnection;

import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    public void executeStatement(String query){
        DefaultTableModel tableModel = this.genericDao.executeStatement(connectionManager.getConnection(), query);
        fireActionPerformed(tableModel);
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

    public ArrayList<String> getDatabaseObjects(String catalog){
        return this.genericDao.getDatabaseObjects(catalog, this.connectionManager.getConnection());
    }
}
