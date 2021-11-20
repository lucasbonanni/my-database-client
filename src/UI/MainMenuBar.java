package UI;

import javax.swing.*;
import java.awt.*;

public class MainMenuBar extends JMenuBar {
    private JMenu archivo;

    private JMenu connections;
    private JMenuItem connectionManager;

    public JMenuItem getConnectionManager() {
        return connectionManager;
    }

    public MainMenuBar(){
        super();
        archivo = new JMenu();
        connections = new JMenu("conexiones");

    }

    public void build(){
        this.setBackground(Color.white);
        archivo.setText("Archivo");
        archivo.setEnabled(false);
        super.add(archivo);
        connectionManager = new JMenuItem("Administrar conexiones");
        connections.add(connectionManager);
        super.add(connections);
    }

    public JMenu getArchivo(){
        return archivo;
    }
}
