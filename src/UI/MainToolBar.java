package UI;

import dbConnection.ConnectionData;
import dbConnection.ConnectionManager;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class MainToolBar extends JToolBar implements ItemListener {

    private final ConnectionManager connectionManager;
    JButton btnExecute;
    JButton btnClearText;
    JButton btnConnect;
    JButton btnDisconnect;
    JComboBox<ConnectionData> connectionsCombo;
    JLabel connectionStatus;
    public JButton getBtnExecute() {
        return btnExecute;
    }

    public MainToolBar(){
        super();
        btnExecute = new JButton("Ejecutar");
        btnClearText = new JButton("Borrar Texto");
        btnDisconnect = new JButton("Desconectar");
        btnConnect = new JButton("Conectar");
        connectionsCombo = new JComboBox<>();
        connectionManager = ConnectionManager.getInstance();
        connectionStatus = new JLabel("Desconectado");
    }

    public void build(){
        setComboData();
        connectionManager.addConnectionsChangedListener((e->setComboData()));
        JPanel panel1 = new JPanel();
        panel1.add(connectionsCombo);
        panel1.add(btnConnect);
        panel1.add(btnDisconnect);
        panel1.add(connectionStatus);
        this.add(panel1);
        JPanel panel2 = new JPanel();
        panel2.add(btnExecute);
        this.add(panel2);
        JPanel panel3 = new JPanel();
        panel3.add(btnClearText);
        this.add(panel3);
        connectionsCombo.addItemListener(this);
        btnConnect.addActionListener((e -> {
            connectionManager.connect();
            this.connectionStatus.setText("Conectado");
        }));
        btnDisconnect.addActionListener((e -> {
            connectionManager.disconnect();
            connectionStatus.setText("Desconectado");
        }));
        connectionManager.setSelectedConnection((ConnectionData) this.connectionsCombo.getSelectedItem());
    }

    private void setComboData(){
        DefaultComboBoxModel connections = new DefaultComboBoxModel<>(((ArrayList<ConnectionData>)connectionManager.getConnections().clone()).toArray()) ;
        connectionsCombo.setModel(connections);
    }

    public void addBtnClearTextAction(ActionListener l){
        btnClearText.addActionListener(l);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
       this.connectionManager.setSelectedConnection((ConnectionData)this.connectionsCombo.getSelectedItem());

    }
}
