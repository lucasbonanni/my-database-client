package dbConnection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    private boolean isConnected;
    private IDatabase database;

    private static final String connectionsFileName = "conections.dat";
    
    public void Connect(){
        this.Connect(this.database);
    }



    private void Connect(IDatabase database) {
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
                    new FileOutputStream(connectionsFileName)));

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

        ConnectionData data1 = new ConnectionData("host 1", 5555,"Conexion 1");
        ConnectionData data2 = new ConnectionData("host 2", 3000,"Conexion 2");
        return new ConnectionData[] {data1,data2};
    }
}
