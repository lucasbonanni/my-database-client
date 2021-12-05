package UI;

import dbConnection.ConnectionData;
import exceptions.ConnectionException;
import dbConnection.GenericService;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame {
    private final GenericService genericService;
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
        btnExecute.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = queryEditorPane.getText();
                try {
                    genericService.executeStatement(text);
                } catch (ConnectionException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error al ejecutar sentencia", JOptionPane.ERROR_MESSAGE);
                }
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
