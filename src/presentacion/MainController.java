package presentacion;

import dao.connection.ConnectionData;
import service.GenericService;
import service.IGenericService;

import javax.swing.*;
import java.awt.*;

public class MainController {
    // main view
    private MainFrame mainFrame;

    //Components
    private final IGenericService genericService;
    GridPane gridPane;
    QueryEditorPane queryEditorPane;
    TreeViewPane treeViewPane;
    MainMenuBar mainMenuBar;
    ConnectionData connectionData;
    MainToolBar toolBar;

    public MainController() {
        this.mainFrame = new MainFrame();
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
        tabbed.add("result 1",gridPane);
        this.mainFrame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
        this.mainFrame.getContentPane().add(treeViewPane, BorderLayout.WEST);
        this.mainFrame.getContentPane().add(queryEditorPane, BorderLayout.CENTER);
        this.mainFrame.getContentPane().add(tabbed, BorderLayout.SOUTH);

        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setSize(new Dimension(500, 500));
    }

    public void showMainFrame()
    {
        this.mainFrame.showFrame();
    }
}
