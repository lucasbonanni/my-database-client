package UI;

import exceptions.ConnectionException;
import service.GenericService;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExecuteEventListener implements MouseListener {

    private QueryEditorPane queryPane;
    private GenericService service;

    public ExecuteEventListener(QueryEditorPane pane, GenericService service) {
        queryPane = pane;
        this.service = service;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String text = queryPane.getText();
        try {
            service.executeStatement(text);
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
}
