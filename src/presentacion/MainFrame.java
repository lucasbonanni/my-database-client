package presentacion;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {


    public MainFrame(){
        super();
    }

    public void showFrame(){
        UIManager.put("MenuBar.background", Color.white);
        this.setBackground(Color.white);
        pack();
        setVisible(true);
    }

}
