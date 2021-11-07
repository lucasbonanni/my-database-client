package dbConnection;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;

public class ExecuteStatamentEvent extends ActionEvent {

    private ResultSet resultSet;
    public ExecuteStatamentEvent(Object[] columns, String[][] data, Object source, int id, String command) {
        super(source, id, command);
        this.columns = columns;
        this.data = data;
    }
/*
    public ExecuteStatamentEvent(Object source, int id, String command, int modifiers) {
        super(source, id, command, modifiers);
    }

    public ExecuteStatamentEvent(Object source, int id, String command, long when, int modifiers) {
        super(source, id, command, when, modifiers);
    }

    public ExecuteStatamentEvent(Object[] columns, String[][] data, Object source, int id, String command, long when, int modifiers){
        this( source,  id,  command,  when,  modifiers);
        this.columns = columns;
        this.data = data;
    }*/
}
