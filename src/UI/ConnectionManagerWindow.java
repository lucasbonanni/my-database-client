package UI;

import dbConnection.ConnectionData;
import dbConnection.ConnectionManager;
import dbConnection.DBDriver;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Vector;

public class ConnectionManagerWindow extends JDialog implements ItemListener {
    private final int pad = 5;

    ConnectionManager connectionManager;
    JComboBox<ConnectionData> connectionsCombo;
    Vector<ConnectionData> connectionDataVector;
    JToolBar toolBar;
    JButton btnNew;
    JButton btnSave;
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

    JButton btnAccept;
    JButton btnCancel;

    private boolean isEditing;
    private boolean isNew;
    private ConnectionData selectedItem;


    public ConnectionManagerWindow(Frame owner) {
        super(owner, "Manager de conexiones",true);
        connectionsCombo = new JComboBox<ConnectionData>();
        toolBar = new JToolBar();
        btnNew = new JButton("Nuevo");
        btnSave = new JButton("Guardar");
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

        btnAccept = new JButton("Acceptar y guardar");
        btnCancel = new JButton("Cancelar");

        connectionManager = ConnectionManager.GetInstance();
    }

    public void build(){
        JPanel connectionsPanel = InitializeData();

        btnNew.addActionListener((e -> {
            this.selectedItem = new ConnectionData();
            this.connectionsCombo.addItem(this.selectedItem);
            this.connectionsCombo.setSelectedItem(this.selectedItem);
        }));
        btnDelete.addActionListener((e -> {
            this.connectionsCombo.removeItem(this.selectedItem);
            this.selectedItem = (ConnectionData) this.connectionsCombo.getSelectedItem();
        }));
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
        this.add(formPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel bottomButtons = new JPanel();
        /*btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });*/
        btnCancel.addActionListener((e) -> { dispose();}); // Dispose de modal
        btnAccept.addActionListener((e) -> {
            connectionManager.setAndSave(this.connectionDataVector);
            dispose();
        });
        bottomButtons.add(btnAccept);
        bottomButtons.add(btnCancel);
        this.add(bottomButtons,BorderLayout.PAGE_END);
    }

    private JPanel InitializeData() {
        this.connectionDataVector = new Vector<ConnectionData>(Arrays.asList(connectionManager.getConnections()));
        connectionsCombo = new JComboBox<ConnectionData>(connectionDataVector);

        connectionsCombo.addItemListener(this);
        this.selectedItem = (ConnectionData) connectionsCombo.getSelectedItem();
        this.fillForm();

        JPanel connectionsPanel = new JPanel();
        connectionsPanel.add(connectionsCombo);
        return connectionsPanel;
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



    @Override
    public void itemStateChanged(ItemEvent e) {
        this.selectedItem = (ConnectionData) e.getItem();
        this.fillForm();
    }

    private void fillForm(){
        this.hostField.setText(this.selectedItem.getHost());
        this.portField.setText("" + this.selectedItem.getPort());
    }
}
