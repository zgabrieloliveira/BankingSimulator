import java.lang.IllegalArgumentException;

public class SaldoInsuficienteException extends IllegalArgumentException {
    SaldoInsuficienteException(String message) {
        super(message);
    }
}

