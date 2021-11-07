package UI;

import javax.swing.*;

public class MainMenuBar extends JMenuBar {
    private JMenu archivo;

    public MainMenuBar(){
        super();
        archivo = new JMenu();
    }

    public void build(){
        /*JMenuItem abrir = new JMenuItem();
        abrir.setText("Abrir");*/
        //archivo.add(abrir);
        archivo.setText("Archivo");
        super.add(archivo);
    }

    public JMenu getArchivo(){
        return archivo;
    }
}
