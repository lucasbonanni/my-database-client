package exceptions;


public class ConnectionException extends Throwable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 855655908947537883L;
	private String errorCode;

    public ConnectionException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = String.valueOf(errorCode);
    }
}
