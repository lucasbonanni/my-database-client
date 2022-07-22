package presentacion;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

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
        tree.setForeground(Color.blue);
        this.setForeground(Color.blue);
        this.setOpaque(false);
        ImageIcon leafIcon = new ImageIcon("images/dbtable.png");
        DefaultTreeCellRenderer renderer =
                new DefaultTreeCellRenderer();
        renderer.setLeafIcon(leafIcon);
        tree.setCellRenderer(renderer);
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
