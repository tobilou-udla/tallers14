package club.Excepciones;

public class SocioYaExisteException extends RuntimeException {
    public SocioYaExisteException(String message) {
        super(message);
    }
}
