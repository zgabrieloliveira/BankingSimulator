import java.lang.IllegalArgumentException;

public class ValorInvalidoException extends IllegalArgumentException {
    ValorInvalidoException(String message) {
        super(message);
    }

}
