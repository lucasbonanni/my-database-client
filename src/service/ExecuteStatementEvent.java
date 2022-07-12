package service;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

public class ExecuteStatementEvent extends ActionEvent {

    private DefaultTableModel resultSet;
    public ExecuteStatementEvent(DefaultTableModel rs, Object source, int id, String command) {
        super(source, id, command);
        this.resultSet =  rs;
    }

    public DefaultTableModel getResultSet() {
        return resultSet;
    }
}
