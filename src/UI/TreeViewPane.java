package UI;

import dbConnection.ConnectionException;
import dbConnection.ConnectionManager;
import dbConnection.GenericService;
import dbConnection.ServiceException;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;

public class TreeViewPane extends JScrollPane {

    private final ConnectionManager connectionManager;
    private final GenericService genericService;
    private JTree tree;
    private DefaultMutableTreeNode rootNode;
    public TreeViewPane() {

        connectionManager = ConnectionManager.getInstance();
        genericService = new GenericService();
        rootNode = new DefaultMutableTreeNode("*");
        tree = new JTree(rootNode);
    }

    public void build(){



        this.connectionManager.addConnectionEstablishedListener((e -> {
            try {
                String catalog = this.connectionManager.getSelectedConnection().getDatabaseName();
                rootNode.setUserObject(catalog);
                ArrayList<String> schemas = this.genericService.getDatabaseObjects(catalog);
                getObjectsTree(rootNode,schemas);
                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                model.reload(rootNode);
            }
            catch (ServiceException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage() + String.format(" (Error code: %s)", ex.getErrorCode()), "Error al establecer la conexión", JOptionPane.ERROR_MESSAGE);
            }
            catch (ConnectionException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al establecer la conexión", JOptionPane.ERROR_MESSAGE);
            }
        }));

        this.connectionManager.addConnectionDisconnectedListener((e -> {
            rootNode.removeAllChildren();
            rootNode.setUserObject("*");
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            model.reload(rootNode);
        }));

        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setViewport(createViewport());
        this.setPreferredSize(new Dimension(200,500));
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        if (tree != null) {
            setViewportView(tree);
        }
        //setUIProperty("opaque",true);
        updateUI();

        if (!this.getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }
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
