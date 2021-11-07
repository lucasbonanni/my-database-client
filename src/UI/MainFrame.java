package UI;

import terminal.Main;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    GridPane gridPane;
    QueryEditorPane queryEditorPane;
    TreeViewPane treeViewPane;
    MainMenuBar mainMenuBar;
    protected EventListenerList listenerList = new EventListenerList();

    public MainFrame(){
        super();
        gridPane = new GridPane();
        queryEditorPane = new QueryEditorPane();
        this.treeViewPane = new TreeViewPane();
        this.mainMenuBar = new MainMenuBar();
    }

    public void build(){
        this.gridPane.build();
        this.queryEditorPane.build();
        this.treeViewPane.build();
        this.mainMenuBar.build();
        var archivo = this.mainMenuBar.getArchivo();
        archivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = queryEditorPane.getText();
                ExecuteQuery(text);
            }
        });

        getContentPane().add(mainMenuBar, BorderLayout.NORTH);
        getContentPane().add(treeViewPane, BorderLayout.WEST);
        getContentPane().add(queryEditorPane, BorderLayout.CENTER);
        getContentPane().add(gridPane, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));
    }

    public void showFrame(){
        pack();
        setVisible(true);
    }

}
