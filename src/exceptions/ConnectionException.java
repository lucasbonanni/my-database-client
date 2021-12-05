package exceptions;

import java.sql.SQLException;

public class ConnectionException extends Throwable {
    private String errorCode;

    public ConnectionException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = String.valueOf(errorCode);
    }
}
