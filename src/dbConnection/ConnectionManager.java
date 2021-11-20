package dbConnection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    private static final String connectionsFileName = "connections.dat";

    private static ConnectionManager instance = null;

    private boolean isConnected;


    private ConnectionManager() {
    }

    public static ConnectionManager GetInstance(){
        if(instance == null){
            instance = new ConnectionManager();
        }
        return instance;
    }

    public boolean IsConnected(){
        return isConnected;
    }
    
    public void Disconnect(){
        
    }



    private void saveConnectionsData(List<ConnectionData> connections) {
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(connectionsFileName,false)));

            for (ConnectionData connectionData: connections) {
                out.writeObject(connectionData);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public List<ConnectionData> loadConnectionsData() {
        List<ConnectionData> resultado = new ArrayList<ConnectionData>();
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(connectionsFileName)));

            while (true) {
                resultado.add((ConnectionData)in.readObject());
            }
        } catch (EOFException e) {
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public ConnectionData[] getConnections(){

        ConnectionData data1 = new ConnectionData("org.postgresql.Driver", "127.0.0.1", 5432,"World", "admin","");
        ConnectionData data2 = new ConnectionData("org.postgresql.Driver", "127.0.0.1", 3000,"Sakila", "admin","password");
        return new ConnectionData[] {data1,data2};
    }

    public void setAndSave(List<ConnectionData> connections) {
        this.saveConnectionsData(connections);
    }
}
