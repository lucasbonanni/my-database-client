package UI;

import dbConnection.ConnectionManager;

import javax.swing.*;
import java.awt.*;

public class TreeViewPane extends JScrollPane {

    private final ConnectionManager connectionManager;

    public TreeViewPane() {
        connectionManager = ConnectionManager.getInstance();
    }

    public void build(){

        JTree view = new JTree();


        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setViewport(createViewport());
        this.setPreferredSize(new Dimension(200,500));
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        if (view != null) {
            setViewportView(view);
        }
        //setUIProperty("opaque",true);
        updateUI();

        if (!this.getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }
    }
}
