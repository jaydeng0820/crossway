package crossway.exception;

public class CrossWayRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 6423935068313738534L;

    public CrossWayRuntimeException(String message) {
        super(message);
    }

    public CrossWayRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrossWayRuntimeException(Throwable cause) {
        super(cause);
    }
}
