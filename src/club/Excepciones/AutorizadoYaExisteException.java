package club.Excepciones;

public class AutorizadoYaExisteException extends RuntimeException {
    public AutorizadoYaExisteException(String message) {
        super(message);
    }
}
