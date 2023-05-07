import java.lang.IllegalArgumentException;

public class ChequeEspecialException extends IllegalArgumentException {
    ChequeEspecialException(String message) {
        super(message);
    }
}