package UI;

import connection.ConnectionData;
import exceptions.ConnectionException;
import service.GenericService;
import service.IGenericService;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame {
    private final IGenericService genericService;
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
        genericService = new GenericService();
    }

    public void build(){
        this.gridPane.build();
        this.queryEditorPane.build();
        this.treeViewPane.build();
        this.mainMenuBar.build();
        this.toolBar.build();
        this.genericService.addListener(gridPane);
        UIManager.put("MenuBar.background",Color.white);
        this.setBackground(Color.white);
        JButton btnExecute = this.toolBar.getBtnExecute();
        btnExecute.addMouseListener(new ExecuteEventListener(this.queryEditorPane,this.genericService));

        toolBar.addBtnClearTextAction((e -> {
            this.queryEditorPane.clearText();
        }));
        JMenuItem connectionManager = this.mainMenuBar.getConnectionManager();
        ConnectionManagerMouseAdapter managerMouseAdapter = new ConnectionManagerMouseAdapter(this);
        connectionManager.addActionListener(managerMouseAdapter);

        this.setJMenuBar(mainMenuBar);
        JTabbedPane tabbed = new JTabbedPane();
        tabbed.setForeground(Color.black);
        tabbed.add("result 1",gridPane);
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
