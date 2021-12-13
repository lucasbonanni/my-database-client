package exceptions;

public class DaoException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4883691814913507249L;
	private String errorCode;

    public DaoException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = String.valueOf(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

}
