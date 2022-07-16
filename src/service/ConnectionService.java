package service;


import dao.ConnectionData;
import dao.DaoException;

import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ConnectionService {
    private static final String connectionsFileName = "connections.dat";

    private static ConnectionService instance = null;

    private ConnectionData selectedConnection;

    ArrayList<ConnectionData> connectionDataVector;

    private EventListenerList connectionsChanged = new EventListenerList();

    private EventListenerList connectionEstablished = new EventListenerList();
    private EventListenerList connectionDisconnected = new EventListenerList();

    private ConnectionService() {
        connectionDataVector = new ArrayList<>();

/*        connectionDataVector.add(new ConnectionData("com.mysql.jdbc.Driver", "127.0.0.1", 3306,"world", "root",""));
        connectionDataVector.add(new ConnectionData("org.postgresql.Driver", "127.0.0.1", 3000,"Sakila", "admin","password"));
        try {
            this.saveConnections(connectionDataVector);
        } catch (ServiceException e) {
            e.printStackTrace();
        }*/
    }

    public static ConnectionService getInstance(){
        if(instance == null){
            instance = new ConnectionService();
        }
        return instance;
    }


    public void saveConnections(ArrayList<ConnectionData> connectionDataVector) throws ServiceException {
        ObjectOutputStream out = null;
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(connectionsFileName,false);
            out = new ObjectOutputStream(new BufferedOutputStream(file));
            out.writeObject(connectionDataVector);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        } finally {
            try {
                out.close();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.connectionDataVector = connectionDataVector;
        this.fireActionPerformed(connectionsChanged,new ActionEvent(connectionDataVector,ActionEvent.ACTION_PERFORMED,"updateConnections"));
    }


    public void loadConnectionsData() throws ServiceException {
        ObjectInputStream in = null;
        FileInputStream file = null;
        try {
            file = new FileInputStream(connectionsFileName);
            in = new ObjectInputStream(file);


            this.connectionDataVector = (ArrayList<ConnectionData>)in.readObject();

        } catch (EOFException e) {
            throw new ServiceException(e.getMessage());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new ServiceException(e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

    public java.sql.Connection getConnection() throws ServiceException {
        try {
            return selectedConnection.getConnection();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e.getErrorCode(),e);
        }
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

    public void connect() throws ServiceException {
        try {
            this.selectedConnection.Connect();
            this.fireActionPerformed(connectionEstablished, new ActionEvent(connectionDataVector,ActionEvent.ACTION_PERFORMED,"connectionEstablished"));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e.getErrorCode(),e);
        }
    }

    public void disconnect() throws ServiceException {
        try {
            this.selectedConnection.disconnect();
            this.fireActionPerformed(connectionDisconnected, new ActionEvent(connectionDataVector,ActionEvent.ACTION_PERFORMED,"connectionDisconnected"));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e.getErrorCode(),e);
        }

    }

    public ConnectionData getSelectedConnection() {
        return this.selectedConnection;
    }
}
