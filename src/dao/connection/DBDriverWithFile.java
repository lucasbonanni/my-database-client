package dao.connection;

import java.util.UUID;

public class DBDriverWithFile implements IDBDriver{
    private String id = UUID.randomUUID().toString();

    private String name;

    private String jarFileName;

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
