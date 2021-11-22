package dbConnection;

public class ConnectionException extends Exception {

    private String errorCode;

    public ConnectionException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = String.valueOf(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

}
