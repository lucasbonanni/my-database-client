package UI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeView {
    public static JScrollPane GetTreeViewPanel(){
        DefaultMutableTreeNode top = new DefaultMutableTreeNode( "Principal");
        JTree tree = new JTree(top);
        JScrollPane jsp = new JScrollPane(tree);
        return jsp;
    }
}
