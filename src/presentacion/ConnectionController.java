package presentacion;

import dao.ConnectionData;
import service.ConnectionService;
import service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConnectionController {
    ConnectionService connectionService;
    ConnectionManagerWindow connectionManagerWindow;
    public ConnectionController(){
        connectionService = ConnectionService.getInstance();
    }

    public void InitializeConnectionManagerWindow(Frame owner){
        connectionManagerWindow = new ConnectionManagerWindow(owner);
    }

    public void Build(){

        connectionService.addConnectionsChangedListener((e) -> setConnectionsComboData());
        setConnectionsComboData();
        connectionManagerWindow.build();
        connectionManagerWindow.getBtnAccept().addActionListener((e) -> {
            try {
                connectionService.saveConnections(getComboItems());
            } catch (ServiceException ex) {
                ex.printStackTrace();
            }
            connectionManagerWindow.dispose();
        });
    }

    public void Show(){
        this.connectionManagerWindow.setVisible(true);
    }


    private void setConnectionsComboData(){
        DefaultComboBoxModel connections = new DefaultComboBoxModel<>(((ArrayList<ConnectionData>) connectionService.getConnections().clone()).toArray()) ;
        this.connectionManagerWindow.getConnectionsCombo().setModel(connections);
    }

    private ArrayList<ConnectionData> getComboItems(){
        ArrayList<ConnectionData> connections = new ArrayList<>();
        for(int i = 0; i < this.connectionManagerWindow.getConnectionsCombo().getItemCount() ; i++ ) {
            connections.add(this.connectionManagerWindow.getConnectionsCombo().getItemAt(i));
        }
        return connections;
    }
}
