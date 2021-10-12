package UI;

import javax.swing.*;
import java.awt.*;

public class SplitPanel {
    JSplitPane splitPane;

    public SplitPanel() {

        JScrollPane treePane = TreeView.GetTreeViewPanel(); // Add tree view

        JSplitPane textPane = NestedSplitPanel.GetNestedSplitPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane,textPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        Dimension minimumSize = new Dimension(100, 50);

        treePane.setMinimumSize(new Dimension(30,50));
        textPane.setMinimumSize(minimumSize);
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("SplitPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SplitPanel splitPaneDemo = new SplitPanel();
        frame.getContentPane().add(splitPaneDemo.getSplitPane());
        frame.setSize(500,500);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
