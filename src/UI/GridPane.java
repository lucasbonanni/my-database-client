package UI;

import dbConnection.ExecuteStatamentEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class GridPane extends JScrollPane implements ActionListener {

    JTable table;

    public GridPane(){
        super();
        Object[] columns = {"*"};
        String[][] data = {{""}};
        table = new JTable(data,columns);
    }

    public void build(){
        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setViewport(createViewport());
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        if (table != null) {
            setViewportView(table);
        }
        //setUIProperty("opaque",true);
        updateUI();

        if (!this.getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }
    }

    public void showResults(ResultSet rs) throws SQLException {
        //this.table = new JTable(data,columns);
        // It creates and displays the table
        table = new JTable(buildTableModel(rs));
        setViewportView(table);
        updateUI();
    }

    public DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // Obtengo las columnas
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // Obtengo las filas
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ExecuteStatamentEvent event = (ExecuteStatamentEvent) e;
        /*JDialog dialog = new JDialog();
        dialog.setPreferredSize(new Dimension(100,100));
        dialog.setTitle("Llego");
        dialog.setVisible(true);*/

        try {
            this.showResults(event.getResultSet());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

