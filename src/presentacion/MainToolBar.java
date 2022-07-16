package presentacion;

import dao.ConnectionData;

import javax.swing.*;

public class MainToolBar extends JToolBar {

    JButton btnExecute;

    public JButton getBtnClearText() {
        return btnClearText;
    }

    public JButton getBtnConnect() {
        return btnConnect;
    }

    public JButton getBtnDisconnect() {
        return btnDisconnect;
    }

    public JComboBox<ConnectionData> getConnectionsCombo() {
        return connectionsCombo;
    }

    public JLabel getConnectionStatus() {
        return connectionStatus;
    }

    private JButton btnClearText;
    private JButton btnConnect;
    private JButton btnDisconnect;
    private JComboBox<ConnectionData> connectionsCombo;
    private JLabel connectionStatus;
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
        connectionStatus = new JLabel("Desconectado");
    }

}
