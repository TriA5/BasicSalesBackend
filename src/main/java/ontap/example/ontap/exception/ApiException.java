package ontap.example.ontap.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
