package presentacion;

import dao.ConnectionData;
import service.ServiceException;
import service.ConnectionService;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class MainToolBar extends JToolBar implements ItemListener {

    private final ConnectionService connectionService;
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
        connectionService = ConnectionService.getInstance();
        connectionStatus = new JLabel("Desconectado");
    }

    public void build() {
        try {
            connectionService.loadConnectionsData();
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al intentar leer el archivo", JOptionPane.ERROR_MESSAGE);
        }
        setComboData();
        connectionService.addConnectionsChangedListener((e->setComboData()));
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
                connectionService.connect();
                this.connectionStatus.setText("Conectado");
            }
            catch (ServiceException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al establecer la conexión", JOptionPane.ERROR_MESSAGE);
            }
        }));
        btnDisconnect.addActionListener((e -> {
            try {
                connectionService.disconnect();
                connectionStatus.setText("Desconectado");
            }
            catch (ServiceException ex){
                JOptionPane.showMessageDialog(this, "Ocurrió un problema al desconectar", "Error al desconectar", JOptionPane.ERROR_MESSAGE);
            }
        }));
        connectionService.setSelectedConnection((ConnectionData) this.connectionsCombo.getSelectedItem());
    }

    private void setComboData(){
        DefaultComboBoxModel connections = new DefaultComboBoxModel<>(((ArrayList<ConnectionData>) connectionService.getConnections().clone()).toArray()) ;
        connectionsCombo.setModel(connections);
    }

    public void addBtnClearTextAction(ActionListener l){
        btnClearText.addActionListener(l);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
       this.connectionService.setSelectedConnection((ConnectionData)this.connectionsCombo.getSelectedItem());

    }
}
