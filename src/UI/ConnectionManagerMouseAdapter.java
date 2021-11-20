package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class ConnectionManagerMouseAdapter implements ActionListener {


    private final Frame main;

    public ConnectionManagerMouseAdapter(Frame main) {
        this.main = main;
    }


    public void mouseClicked(MouseEvent e) {
        ConnectionManagerWindow managerWindow = new ConnectionManagerWindow(this.main);
        managerWindow.build();
        managerWindow.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ConnectionManagerWindow managerWindow = new ConnectionManagerWindow(this.main);
        managerWindow.build();
        managerWindow.setVisible(true);
    }
}
