package presentacion;

import service.ExecuteStatementEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class GridController implements ActionListener {
    public GridPane getGridPane() {
        return gridPane;
    }

    GridPane gridPane;
    JTable table;
    public GridController(){
        this.gridPane = new GridPane();
        initResultTable();
    }

    public void initResultTable(){
        Object[] columns = {"*"};
        String[][] data = {{""}};
        table = new JTable(data,columns);
    }

    public void build(){
        this.gridPane.build();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ExecuteStatementEvent event = (ExecuteStatementEvent) e;

        try {
            this.showResults(event.getResultSet());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void showResults(DefaultTableModel model) throws SQLException {
        // It creates and displays the table
        table = new JTable(model);
        table.getTableHeader().setBackground(Color.lightGray);
        table.setAutoCreateRowSorter(true);
        TableRowSorter<DefaultTableModel> sorter
                = new TableRowSorter<DefaultTableModel>((DefaultTableModel) table.getModel());
        table.setRowSorter(sorter);
        this.gridPane.setViewportView(table);
        this.gridPane.updateUI();
    }
}
