package connection;

public interface IDBDriver {

    String getDatabaseType();

    void registerDriver();
}
