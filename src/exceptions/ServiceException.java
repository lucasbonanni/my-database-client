package exceptions;


public class ServiceException extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3386571682787445439L;
	private String errorCode;

    public ServiceException(String message)
    {
        super(message);
    }

    public ServiceException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ServiceException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = String.valueOf(errorCode);
    }



    public String getErrorCode() {
        return errorCode;
    }
}
