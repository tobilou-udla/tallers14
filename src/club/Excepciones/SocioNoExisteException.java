package club.Excepciones;

public class SocioNoExisteException extends RuntimeException {
    public SocioNoExisteException(String message) {
        super(message);
    }
}
