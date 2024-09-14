package exceptions;

public class ServerSaveException extends RuntimeException {
    public ServerSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
