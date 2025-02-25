import java.util.InputMismatchException;

public class ErroDigitoException extends InputMismatchException {
    public ErroDigitoException(String mensagem) {
        super(mensagem);
    }
}
