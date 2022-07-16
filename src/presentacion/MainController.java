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
        this.toolBar.build();
        this.genericService.addListener(this.gridController);
        buildMainFrame();
    }

    private void buildMainFrame() {
        JButton btnExecute = this.toolBar.getBtnExecute();
        btnExecute.addMouseListener(new ExecuteEventListener(this.queryEditorPane,this.genericService));

        toolBar.addBtnClearTextAction((e -> {
            this.queryEditorPane.clearText();
        }));
        JMenuItem connectionManager = this.mainMenuBar.getConnectionManager();
        ConnectionManagerMouseAdapter managerMouseAdapter = new ConnectionManagerMouseAdapter(this.mainFrame);
        connectionManager.addActionListener(managerMouseAdapter);

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
}
