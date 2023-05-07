import java.lang.RuntimeException;

public class ContaInativaException extends RuntimeException {
    ContaInativaException(String message) {
        super(message);
    }
}
