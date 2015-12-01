package it.geosolutions.vibi.mapper.exceptions;

public class VibiException extends RuntimeException {

    public VibiException(String format, Object ... arguments) {
        super(String.format(format, arguments));
    }

    public VibiException(Throwable cause, String format, Object ... arguments) {
        super(String.format(format, arguments), cause);
    }
}
