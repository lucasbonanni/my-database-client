package UI;

import javax.swing.*;

public class MainToolBar extends JToolBar {

    JButton btnExecute;
    JButton btnClearText;
    JButton btnConnect;
    JButton btnDisconect;

    public JButton getBtnExecute() {
        return btnExecute;
    }

    public MainToolBar(){
        super();
        btnExecute = new JButton("Ejecutar");
        btnClearText = new JButton("Borrar Texto");
        btnDisconect = new JButton("Desconectar");
        btnConnect = new JButton("Conectar");
    }

    public void build(){
        JPanel panel1 = new JPanel();
        panel1.add(btnConnect);
        panel1.add(btnDisconect);
        this.add(panel1);
        JPanel panel2 = new JPanel();
        panel2.add(btnExecute);
        this.add(panel2);
        JPanel panel3 = new JPanel();
        panel3.add(btnClearText);
        this.add(panel3);
    }

}
