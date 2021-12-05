package UI;

import connection.ConnectionData;
import exceptions.ConnectionException;
import connection.ConnectionManager;

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
        ImageIcon executeIcon = new ImageIcon("images/Play-icon.png");
        ImageIcon eraseIcon = new ImageIcon("images/eraser-icon.png");
        btnExecute = new JButton(executeIcon);
        btnClearText = new JButton(eraseIcon);
        btnDisconnect = new JButton("Desconectar");
        btnConnect = new JButton("Conectar");
        connectionsCombo = new JComboBox<>();
        connectionManager = ConnectionManager.getInstance();
        connectionStatus = new JLabel("Desconectado");
    }

    public void build(){
        connectionManager.loadConnectionsData();
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
            try {
                connectionManager.connect();
                this.connectionStatus.setText("Conectado");
            }
            catch (ConnectionException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al establecer la conexión", JOptionPane.ERROR_MESSAGE);
            }
        }));
        btnDisconnect.addActionListener((e -> {
            try {
                connectionManager.disconnect();
                connectionStatus.setText("Desconectado");
            }
            catch (ConnectionException ex){
                JOptionPane.showMessageDialog(this, "Ocurrió un problema al desconectar", "Error al desconectar", JOptionPane.ERROR_MESSAGE);
            }
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
