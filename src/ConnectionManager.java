public class ConnectionManager {
    private boolean isConnected;
    private IDatabase database;
    
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
}
