package dbConnection;

import java.util.UUID;

public class DBDriver implements IDBDriver {
    private String _id = UUID.randomUUID().toString();

    private String _name;

    private String _driverClassName;

    private String _url;

    private boolean _loaded;

    @Override
    public String getDatabaseType() {
        return null;
    }

    @Override
    public void registerDriver() {

    }
}
