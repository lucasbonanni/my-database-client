package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainPanel extends JFrame {
    JMenuBar mb;

    public MainPanel() {
        super("SQL Client");
        mb = new JMenuBar();
        JMenu fileMenu = createMenu("Archivo", KeyEvent.VK_F,mb);
        mb.add(fileMenu);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SplitPanel splitPaneDemo = new SplitPanel();
        getContentPane().add(mb, BorderLayout.NORTH);
        getContentPane().add(splitPaneDemo.getSplitPane(),BorderLayout.CENTER);
        setSize(500,500);
        //Display the window.
        pack();
        setVisible(true);
    }

    JMenu createMenu(String s,int key,JMenuBar toMenuBar)
    {
        JMenu temp=new JMenu(s);
        temp.setMnemonic(key);
        toMenuBar.add(temp);
        return temp;
    }
}
