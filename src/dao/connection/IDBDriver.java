package dao.connection;

public interface IDBDriver {

    String getDatabaseType();

    void registerDriver();
}
