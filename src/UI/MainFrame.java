package UI;

import dbConnection.ConnectionData;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame {
    GridPane gridPane;
    QueryEditorPane queryEditorPane;
    TreeViewPane treeViewPane;
    MainMenuBar mainMenuBar;
    ConnectionData connectionData;
    MainToolBar toolBar;
    protected EventListenerList listenerList = new EventListenerList();

    public MainFrame(){
        super();
        gridPane = new GridPane();
        queryEditorPane = new QueryEditorPane();
        this.treeViewPane = new TreeViewPane();
        this.mainMenuBar = new MainMenuBar();
        connectionData = new ConnectionData();
        toolBar = new MainToolBar();
    }

    public void build(){
        this.gridPane.build();
        this.queryEditorPane.build();
        this.treeViewPane.build();
        this.mainMenuBar.build();
        this.toolBar.build();
        this.connectionData.addListener(gridPane);
        JButton btnExecute = this.toolBar.getBtnExecute();
        btnExecute.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = queryEditorPane.getText();
                connectionData.executeQuery(text);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
/*        archivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = queryEditorPane.getText();
                connectionData.executeQuery(text);
            }
        });*/

        this.setJMenuBar(mainMenuBar);
        JTabbedPane tabbed = new JTabbedPane();
        tabbed.setForeground(Color.black);
        tabbed.add("result 1",gridPane);
        tabbed.add("result 2",new JPanel());
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(treeViewPane, BorderLayout.WEST);
        getContentPane().add(queryEditorPane, BorderLayout.CENTER);
        getContentPane().add(tabbed, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));
    }

    public void showFrame(){
        pack();
        setVisible(true);
    }

}
