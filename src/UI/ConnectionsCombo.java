package UI;

import dbConnection.ConnectionData;
import dbConnection.ConnectionManager;

import javax.swing.*;

public class ConnectionsCombo extends JComboBox<ConnectionData> {

    private final ConnectionManager connectionManager;

    public ConnectionsCombo() {
        super();
        connectionManager = ConnectionManager.getInstance();
    }

    public void loadConnections(){
        DefaultComboBoxModel<ConnectionData> connections = new DefaultComboBoxModel(connectionManager.getConnections());
        this.setModel(connections);
        updateUI();
    }

    public void build(){
        loadConnections();
    }
}
