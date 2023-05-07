import java.io.IOException;

public class SenhaInvalidaException extends IOException {
    SenhaInvalidaException(String message) {
        super(message);
    }
}
