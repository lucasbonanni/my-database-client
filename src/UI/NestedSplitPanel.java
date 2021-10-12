package UI;

import javax.swing.*;

public class NestedSplitPanel {
    public static JSplitPane GetNestedSplitPanel(){
        JScrollPane gridPanel = GridPanel.GetGridPanel();// Add tree view

        JScrollPane textPane = TextPanel.GetTextPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textPane,gridPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        return splitPane;
    }
}
