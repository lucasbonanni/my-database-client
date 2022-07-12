package presentacion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionManagerMouseAdapter implements ActionListener {


    private final Frame main;

    public ConnectionManagerMouseAdapter(Frame main) {
        this.main = main;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ConnectionManagerWindow managerWindow = new ConnectionManagerWindow(this.main);
        managerWindow.build();
        managerWindow.setVisible(true);
    }
}
