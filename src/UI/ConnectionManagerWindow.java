package UI;

import dbConnection.ConnectionData;
import dbConnection.DBDriver;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

public class ConnectionManagerWindow extends JDialog {
    private final int pad = 5;

    JComboBox<ConnectionData> connections;
    JToolBar toolBar;
    JButton btnNew;
    JButton btnDelete;
    JTextField hostField;
    JFormattedTextField portField;
    JTextField userField;
    JPasswordField passwordField;
    JComboBox<DBDriver> driverJComboBox;

    JLabel hostLbl;
    JLabel portLbl;
    JLabel userLbl;
    JLabel passwordLbl;
    JLabel driverLbl;

    public ConnectionManagerWindow(Frame owner) {
        super(owner, "Manager de conexiones",true);
        connections = new JComboBox<ConnectionData>();
        toolBar = new JToolBar();
        btnNew = new JButton("Nuevo");
        btnDelete = new JButton("Eliminar");
        hostField = new JTextField();
        portField = new JFormattedTextField(createHostFormatter("####"));
        userField = new JTextField();
        passwordField = new JPasswordField();
        driverJComboBox = new JComboBox<DBDriver>();
        this.setBounds(20,20,500,300);

        hostLbl = new JLabel("Host");
        portLbl = new JLabel("Port");
        userLbl = new JLabel("Usuario");
        passwordLbl = new JLabel("Contraseña");
        driverLbl = new JLabel("Driver");
    }

    public void build(){
        JPanel connectionsPanel = new JPanel();
        connectionsPanel.add(connections);
        JPanel buttons = new JPanel();
        buttons.add(btnNew);
        buttons.add(btnDelete);
        toolBar.add(connectionsPanel);
        toolBar.add(buttons);
        this.add(toolBar, BorderLayout.PAGE_START);
        // build the form
        hostField.setColumns(20);

        portField.setColumns(4);
        passwordField.setColumns(15);
        userField.setColumns(15);

        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(200,200));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = pad;
        constraints.ipadx = pad;
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(hostLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        formPanel.add(hostField,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 2;
        constraints.gridy = 0;
        formPanel.add(portLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 0;
        formPanel.add(portField,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(userLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 1;
        formPanel.add(userField,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(passwordLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 2;
        formPanel.add(passwordField,constraints);
        this.add(formPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }


    protected MaskFormatter createHostFormatter(String mask) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(mask);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }


}
