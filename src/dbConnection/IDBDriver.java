package dbConnection;

public interface IDBDriver {

    String getDatabaseType();

    void registerDriver();
}
