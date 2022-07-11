package service;

import exceptions.ConnectionException;
import exceptions.ServiceException;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface IGenericService {
    void addListener(ActionListener actionListener);

    void executeStatement(String query) throws ServiceException;

    ArrayList<String> getDatabaseObjects(String catalog) throws ServiceException, ConnectionException;
}
