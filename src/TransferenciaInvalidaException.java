import java.lang.IllegalArgumentException;

public class TransferenciaInvalidaException extends IllegalArgumentException {
    TransferenciaInvalidaException(String message) {
        super(message);
    }
}

