package UI;

import javax.swing.*;
import java.awt.*;

public class MainMenuBar extends JMenuBar {
    private JMenu archivo;

    public MainMenuBar(){
        super();
        archivo = new JMenu();
    }

    public void build(){
        this.setBackground(Color.white);
        archivo.setText("Archivo");
        super.add(archivo);
    }

    public JMenu getArchivo(){
        return archivo;
    }
}
