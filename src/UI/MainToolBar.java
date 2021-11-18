package UI;

import javax.swing.*;

public class MainToolBar extends JToolBar {

    JButton btnExecute;

    public JButton getBtnExecute() {
        return btnExecute;
    }

    public MainToolBar(){
        super();
        btnExecute = new JButton("Ejecutar");
    }

    public void build(){
        this.add(btnExecute);
    }

}
