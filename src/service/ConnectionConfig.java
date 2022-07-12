package service;

public class ConnectionConfig {
/*
    private static final String connectionsFileName = "connections.dat";

    public void saveConnections(ArrayList<ConnectionData> connectionDataVector) {
        ObjectOutputStream out = null;
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(connectionsFileName,false);
            out = new ObjectOutputStream(new BufferedOutputStream(file));
            out.writeObject(connectionDataVector);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        this.connectionDataVector = connectionDataVector;
//        this.fireActionPerformed(connectionsChanged,new ActionEvent(connectionDataVector,ActionEvent.ACTION_PERFORMED,"updateConnections"));
    }


    public void loadConnectionsData() throws ServiceException {
        ObjectInputStream in = null;
        FileInputStream file = null;
        try {
            file = new FileInputStream(connectionsFileName);
            in = new ObjectInputStream(file);


//            this.connectionDataVector = (ArrayList<ConnectionData>)in.readObject();

        } catch (EOFException e) {
            throw new ServiceException(e.getMessage());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        } catch (ClassNotFoundException e) {
//            throw new FileException(e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }*/
}
