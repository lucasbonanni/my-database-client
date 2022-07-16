package presentacion;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class TreeViewPane extends JScrollPane {


    public JTree getTree() {
        return tree;
    }

    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
    }

    private JTree tree;
    private DefaultMutableTreeNode rootNode;

    public TreeViewPane() {
        rootNode = new DefaultMutableTreeNode("*");
        tree = new JTree(rootNode);

    }

    public void build(){
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
        updateUI();

        if (!this.getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }
    }


}
