package dbConnection;

import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    private static final String connectionsFileName = "connections.dat";

    private static ConnectionManager instance = null;

    private ConnectionData selectedConnection;

    ArrayList<ConnectionData> connectionDataVector;

    private EventListenerList connectionsChanged = new EventListenerList();

    private EventListenerList connectionEstablished = new EventListenerList();
    private EventListenerList connectionDisconnected = new EventListenerList();

    private ConnectionManager() {
        connectionDataVector = new ArrayList<>();
        connectionDataVector.add(new ConnectionData("com.mysql.jdbc.Driver", "127.0.0.1", 3306,"world", "root",""));
        connectionDataVector.add(new ConnectionData("org.postgresql.Driver", "127.0.0.1", 3000,"Sakila", "admin","password"));
    }

    public static ConnectionManager getInstance(){
        if(instance == null){
            instance = new ConnectionManager();
        }
        return instance;
    }

    /*public boolean IsConnected(){
        return selectedConnection.isConnected();
    }*/

    public void saveConnections(ArrayList<ConnectionData> connectionDataVector) {
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(connectionsFileName,false)));

            for (ConnectionData connectionData: connectionDataVector) {
                out.writeObject(connectionData);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.connectionDataVector = connectionDataVector;
        this.fireActionPerformed(connectionsChanged,new ActionEvent(connectionDataVector,ActionEvent.ACTION_PERFORMED,"updateConnections"));
    }


    public List<ConnectionData> loadConnectionsData() {
        List<ConnectionData> resultado = new ArrayList<ConnectionData>();
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(connectionsFileName)));

            while (true) {
                resultado.add((ConnectionData)in.readObject());
            }
        } catch (EOFException e) {
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public java.sql.Connection getConnection()
    {
        return selectedConnection.getConnection();
    }

    public ArrayList<ConnectionData> getConnections(){
        return this.connectionDataVector;
    }



    protected void fireActionPerformed(EventListenerList list, ActionEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = list.getListenerList();
        ActionEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]== ActionListener.class) {
                String actionCommand = event.getActionCommand();
                e = new ActionEvent(event.getSource(),
                        ActionEvent.ACTION_PERFORMED,
                        actionCommand,
                        event.getWhen(),
                        event.getModifiers());
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

    public void addConnectionsChangedListener(ActionListener actionListener) {
        this.connectionsChanged.add(ActionListener.class,actionListener);
    }

    public void addConnectionEstablishedListener(ActionListener actionListener) {
        this.connectionEstablished.add(ActionListener.class,actionListener);
    }

    public void addConnectionDisconnectedListener(ActionListener actionListener) {
        this.connectionDisconnected.add(ActionListener.class,actionListener);
    }

    public void setSelectedConnection(ConnectionData selectedItem) {
        this.selectedConnection = selectedItem;
    }

    public void connect() {
        this.selectedConnection.Connect();
        this.fireActionPerformed(connectionEstablished, new ActionEvent(connectionDataVector,ActionEvent.ACTION_PERFORMED,"connectionEstablished"));
    }

    public void disconnect(){
        this.selectedConnection.disconnect();
        this.fireActionPerformed(connectionDisconnected, new ActionEvent(connectionDataVector,ActionEvent.ACTION_PERFORMED,"connectionDisconnected"));
    }

    public ConnectionData getSelectedConnection() {
       return this.selectedConnection;
    }
}
