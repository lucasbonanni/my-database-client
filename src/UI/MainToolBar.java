package UI;

import dbConnection.ConnectionData;
import dbConnection.ConnectionManager;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainToolBar extends JToolBar {

    private final ConnectionManager connectionManager;
    JButton btnExecute;
    JButton btnClearText;
    JButton btnConnect;
    JButton btnDisconnect;
    JComboBox<ConnectionData> connectionsCombo;
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
    }

    public void build(){
        setComboData();
        connectionManager.addConnectionsChangedListener((e->setComboData()));
        JPanel panel1 = new JPanel();
        panel1.add(connectionsCombo);
        panel1.add(btnConnect);
        panel1.add(btnDisconnect);
        this.add(panel1);
        JPanel panel2 = new JPanel();
        panel2.add(btnExecute);
        this.add(panel2);
        JPanel panel3 = new JPanel();
        panel3.add(btnClearText);
        this.add(panel3);
    }

    private void setComboData(){
        DefaultComboBoxModel connections = new DefaultComboBoxModel<>(((ArrayList<ConnectionData>)connectionManager.getConnections().clone()).toArray()) ;
        connectionsCombo.setModel(connections);
    }

    public void addBtnClearTextAction(ActionListener l){
        btnClearText.addActionListener(l);
    }

}
