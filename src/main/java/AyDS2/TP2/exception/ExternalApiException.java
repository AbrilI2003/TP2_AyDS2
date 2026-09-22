package AyDS2.TP2.exception;

public class ExternalApiException extends RuntimeException {

    public ExternalApiException(String mensaje) {
        super(mensaje);
    }
}