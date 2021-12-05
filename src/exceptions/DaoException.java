package exceptions;

public class DaoException extends Exception {

    private String errorCode;

    public DaoException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = String.valueOf(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

}
