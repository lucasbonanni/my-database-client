package presentacion;

import dao.connection.ConnectionData;
import service.ConnectionService;
import service.GenericService;
import service.IGenericService;
import service.ServiceException;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainController {
    // main view
    private MainFrame mainFrame;

    //Components
    private final IGenericService genericService;
    private final ConnectionService connectionService;

    QueryEditorPane queryEditorPane;
    TreeViewPane treeViewPane;
    MainMenuBar mainMenuBar;
    ConnectionData connectionData;
    MainToolBar toolBar;
    GridController gridController;

    public MainController() {
        this.initMainFrame();
        this.gridController = new GridController();
        connectionService = ConnectionService.getInstance();
        genericService = new GenericService();
    }

    public void initMainFrame(){
        this.mainFrame = new MainFrame();
        queryEditorPane = new QueryEditorPane();
        this.treeViewPane = new TreeViewPane();
        this.mainMenuBar = new MainMenuBar();
        connectionData = new ConnectionData();
        toolBar = new MainToolBar();
    }

    public void build(){
        this.gridController.build();
        this.queryEditorPane.build();
        buildTreeView();
        this.mainMenuBar.build();
        this.buildMainToolbar();
        this.genericService.addListener(this.gridController);
        buildMainFrame();
    }

    private void buildMainFrame() {
        JButton btnExecute = this.toolBar.getBtnExecute();
        //btnExecute.addMouseListener(new ExecuteEventListener(this.queryEditorPane,this.genericService));
        btnExecute.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = queryEditorPane.getText();
                try {
                    genericService.executeStatement(text);
                } catch (ServiceException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error al ejecutar sentencia", JOptionPane.ERROR_MESSAGE);
                }
                treeViewPane.getRootNode().removeAllChildren();
                buildTreeViewTables();
            }
        });

        toolBar.getBtnClearText().addActionListener((e -> {
            this.queryEditorPane.clearText();
        }));
        JMenuItem connectionManager = this.mainMenuBar.getConnectionManager();
        connectionManager.addActionListener(e -> {
            ConnectionController connectionController = new ConnectionController();
            connectionController.InitializeConnectionManagerWindow(this.mainFrame);
            connectionController.Build();
            connectionController.Show();
        });

        this.mainFrame.setJMenuBar(mainMenuBar);
        JTabbedPane tabbed = new JTabbedPane();
        tabbed.setForeground(Color.black);
        tabbed.add("result 1",this.gridController.getGridPane());
        this.mainFrame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
        this.mainFrame.getContentPane().add(treeViewPane, BorderLayout.WEST);
        this.mainFrame.getContentPane().add(queryEditorPane, BorderLayout.CENTER);
        this.mainFrame.getContentPane().add(tabbed, BorderLayout.SOUTH);

        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setSize(new Dimension(500, 500));
    }

    public void buildTreeView(){
        this.connectionService.addConnectionEstablishedListener((e -> {
            buildTreeViewTables();
        }));

        this.connectionService.addConnectionDisconnectedListener((e -> {
            this.treeViewPane.getRootNode().removeAllChildren();
            this.treeViewPane.getRootNode().setUserObject("*");
            DefaultTreeModel model = (DefaultTreeModel) this.treeViewPane.getTree().getModel();
            model.reload(this.treeViewPane.getRootNode());
        }));
        this.treeViewPane.getTree().addTreeSelectionListener((e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.treeViewPane.getTree().getLastSelectedPathComponent();
            if(node.isLeaf()){
                this.queryEditorPane.setText("select * from " + node);
            }
        }));
        this.treeViewPane.build();
    }

    private void buildTreeViewTables() {
        try {
            String catalog = this.connectionService.getSelectedConnection().getDatabaseName();
            this.treeViewPane.getRootNode().setUserObject(catalog);
            ArrayList<String> schemas = this.genericService.getDatabaseObjects(catalog);
            getObjectsTree(this.treeViewPane.getRootNode(),schemas);
            DefaultTreeModel model = (DefaultTreeModel) this.treeViewPane.getTree().getModel();
            model.reload(this.treeViewPane.getRootNode());
        }
        catch (ServiceException ex){
            JOptionPane.showMessageDialog(this.treeViewPane, ex.getMessage() + String.format(" (Error code: %s)", ex.getErrorCode()), "Error al establecer la conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buildMainToolbar(){

        try {
            connectionService.loadConnectionsData();
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this.toolBar, ex.getMessage(), "Error al intentar leer el archivo", JOptionPane.ERROR_MESSAGE);
        }
        setComboData();
        connectionService.addConnectionsChangedListener((e->setComboData()));
        JPanel panel1 = new JPanel();
        panel1.add(this.toolBar.getConnectionsCombo());
        panel1.add(this.toolBar.getBtnConnect()  );
        panel1.add(this.toolBar.getBtnDisconnect() );
        panel1.add(this.toolBar.getConnectionStatus() );
        this.toolBar.add(panel1);
        JPanel panel2 = new JPanel();
        panel2.add(this.toolBar.getBtnExecute());
        this.toolBar.add(panel2);
        JPanel panel3 = new JPanel();
        panel3.add(this.toolBar.getBtnClearText());
        this.toolBar.add(panel3);
        this.toolBar.getConnectionsCombo().addItemListener(e -> connectionService.setSelectedConnection((dao.ConnectionData)toolBar.getConnectionsCombo().getSelectedItem()));
        this.toolBar.getBtnConnect().addActionListener((e -> {
            try {
                connectionService.connect();
                this.toolBar.getConnectionStatus().setText("Conectado");
            }
            catch (ServiceException ex){
                JOptionPane.showMessageDialog(this.toolBar, ex.getMessage(), "Error al establecer la conexión", JOptionPane.ERROR_MESSAGE);
            }
        }));
        this.toolBar.getBtnDisconnect().addActionListener((e -> {
            try {
                connectionService.disconnect();
                this.toolBar.getConnectionStatus().setText("Desconectado");
            }
            catch (ServiceException ex){
                JOptionPane.showMessageDialog(this.toolBar, "Ocurrió un problema al desconectar", "Error al desconectar", JOptionPane.ERROR_MESSAGE);
            }
        }));
        connectionService.setSelectedConnection((dao.ConnectionData) this.toolBar.getConnectionsCombo().getSelectedItem());

    }

    public void showMainFrame()
    {
        this.mainFrame.showFrame();
    }


    private void getObjectsTree(DefaultMutableTreeNode root, ArrayList<String> schemas) {
        DefaultMutableTreeNode tables =new DefaultMutableTreeNode("Tables");
        DefaultMutableTreeNode views =new DefaultMutableTreeNode("Views");

        root.add(tables);
        root.add(views);

        for (String result: schemas) {
            String[] nameParts = result.split("\\.");

            if("TABLE".equals(nameParts[0].toUpperCase())){
                tables.add(new DefaultMutableTreeNode(nameParts[1]));
            }
            if("VIEW".equals(nameParts[0].toUpperCase())) {
                views.add(new DefaultMutableTreeNode(nameParts[1]));
            }
        }
    }

    private void setComboData(){
        DefaultComboBoxModel connections = new DefaultComboBoxModel<>(((ArrayList<dao.ConnectionData>) connectionService.getConnections().clone()).toArray()) ;
        this.toolBar.getConnectionsCombo().setModel(connections);
    }
}
