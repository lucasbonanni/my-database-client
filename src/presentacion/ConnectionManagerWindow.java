package presentacion;

import dao.ConnectionData;
import service.ServiceException;
import service.ConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.text.NumberFormat;
public class ConnectionManagerWindow extends JDialog implements ItemListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6841453153183086209L;

	private final int pad = 5;

    ConnectionManager connectionManager;
    JComboBox<ConnectionData> connectionsCombo;

    JToolBar toolBar;

    JTextField hostField;
    JFormattedTextField portField;
    JTextField userField;
    JTextField databaseNameField;
    JPasswordField passwordField;
    JTextField driverNameField;


    JLabel hostLbl;
    JLabel portLbl;
    JLabel userLbl;
    JLabel passwordLbl;
    JLabel driverLbl;
    JLabel databaseNameLbl;


    JButton btnNew;
    JButton btnSave;
    JButton btnDelete;
    JButton btnAccept;
    JButton btnCancel;


    private ConnectionData selectedItem;
    private JComboBox<String> driverClassCombo;

    public ConnectionManagerWindow(Frame owner) {
        super(owner, "Manager de conexiones",true);
        toolBar = new JToolBar();
        initializeFields();
        initializeLabels();
        initializeButtons();
        connectionManager = ConnectionManager.getInstance();

        connectionsCombo = new JComboBox<>();
        driverClassCombo = new JComboBox<>();
        setDriverClassComboData();
        this.setBounds(20,20,500,300);
    }

    private void initializeButtons() {
        btnAccept = new JButton("Aceptar y guardar");
        btnCancel = new JButton("Cancelar");
        btnNew = new JButton("Nuevo");
        btnSave = new JButton("Guardar");
        btnDelete = new JButton("Eliminar");
        btnSave = new JButton("Guardar");
    }

    private void initializeLabels() {
        hostLbl = new JLabel("Host");
        portLbl = new JLabel("Port");
        userLbl = new JLabel("Usuario");
        passwordLbl = new JLabel("Contraseña");
        driverLbl = new JLabel("Driver");
        databaseNameLbl = new JLabel("Base de datos");
        driverLbl = new JLabel("Driver");
    }

    private void initializeFields() {
        hostField = new JTextField();
        portField = new JFormattedTextField(createHostFormatter());
        userField = new JTextField();
        databaseNameField = new JTextField();
        passwordField = new JPasswordField();
        driverNameField = new JTextField();

    }

    public void build(){
        JPanel connectionsPanel = InitializeData();
        connectionManager.addConnectionsChangedListener((e) -> setConnectionsComboData());

        btnNew.addActionListener((e -> {
            this.selectedItem = new ConnectionData();
            fillForm();
        }));
        btnDelete.addActionListener((e -> {
            this.connectionsCombo.removeItem(this.selectedItem);
            this.selectedItem = (ConnectionData) this.connectionsCombo.getSelectedItem();
        }));
        btnSave.addActionListener((e -> {
            this.selectedItem.setDatabaseName(this.databaseNameField.getText());
            this.selectedItem.setDriverName(this.driverNameField.getText());
            this.selectedItem.setDriverName((String)this.driverClassCombo.getSelectedItem());
            this.selectedItem.setHost(this.hostField.getText());
            String pass = String.valueOf(this.passwordField.getPassword());
            this.selectedItem.setPassword(pass);
            this.selectedItem.setUserName(this.userField.getText());
            this.selectedItem.setPort(Integer.parseInt(this.portField.getText()));
            this.connectionsCombo.addItem(this.selectedItem);
            this.connectionsCombo.updateUI();
        }));
        JPanel buttons = new JPanel();
        buttons.add(btnNew);
        buttons.add(btnDelete);
        buttons.add(btnSave);
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
        formPanel.add(driverLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        formPanel.add(driverClassCombo,constraints);
        // Cambiar desde acá
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = pad;
        constraints.ipadx = pad;
        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(hostLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 1;
        formPanel.add(hostField,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 2;
        constraints.gridy = 1;
        formPanel.add(portLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 1;
        formPanel.add(portField,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(databaseNameLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 2;
        formPanel.add(databaseNameField,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        formPanel.add(userLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 3;
        formPanel.add(userField,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        formPanel.add(passwordLbl,constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 4;
        formPanel.add(passwordField,constraints);
        this.add(formPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel bottomButtons = new JPanel();

        btnCancel.addActionListener((e) -> dispose()); // Dispose de modal
        btnAccept.addActionListener((e) -> {
            try {
                connectionManager.saveConnections(getComboItems());
            } catch (ServiceException ex) {
                ex.printStackTrace();
            }
            dispose();
        });
        bottomButtons.add(btnAccept);
        bottomButtons.add(btnCancel);
        this.add(bottomButtons,BorderLayout.PAGE_END);
    }

    private JPanel InitializeData() {
        setConnectionsComboData();
        connectionsCombo.addItemListener(this);
        this.selectedItem = (ConnectionData) connectionsCombo.getSelectedItem();
        this.fillForm();

        JPanel connectionsPanel = new JPanel();
        connectionsPanel.add(connectionsCombo);
        return connectionsPanel;
    }


    protected NumberFormat createHostFormatter() {
        return NumberFormat.getNumberInstance();
    }



    @Override
    public void itemStateChanged(ItemEvent e) {
        this.selectedItem = (ConnectionData) e.getItem();
        this.fillForm();
    }

    private void fillForm(){
        this.hostField.setText(this.selectedItem.getHost());
        this.portField.setText("" + this.selectedItem.getPort());
        this.databaseNameField.setText("" + this.selectedItem.getDatabaseName());
        this.userField.setText("" + this.selectedItem.getUser());
        this.passwordField.setText("" + this.selectedItem.getPassword());
        this.driverNameField.setText("" + this.selectedItem.getDriverName());
        this.driverClassCombo.setSelectedItem(this.selectedItem.getDriverName());
    }

    private void setConnectionsComboData(){
        DefaultComboBoxModel connections = new DefaultComboBoxModel<>(((ArrayList<ConnectionData>)connectionManager.getConnections().clone()).toArray()) ;
        connectionsCombo.setModel(connections);
    }

    private void setDriverClassComboData(){
        this.driverClassCombo.addItem("com.mysql.jdbc.Driver");
        this.driverClassCombo.addItem("org.h2.Driver");
        this.driverClassCombo.addItem("Personalizado (Proximamente)");
    }

    private ArrayList<ConnectionData> getComboItems(){
        ArrayList<ConnectionData> connections = new ArrayList<>();
        for(int i = 0; i < connectionsCombo.getItemCount() ; i++ ) {
            connections.add(connectionsCombo.getItemAt(i));
        }
        return connections;
    }
}
