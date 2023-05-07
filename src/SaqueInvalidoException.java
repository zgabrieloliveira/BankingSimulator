import java.lang.IllegalArgumentException;

public class SaqueInvalidoException extends IllegalArgumentException {
    SaqueInvalidoException(String message) {
        super(message);
    }
}

