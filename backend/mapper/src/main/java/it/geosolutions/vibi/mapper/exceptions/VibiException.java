package it.geosolutions.vibi.mapper.exceptions;

public class VibiException extends RuntimeException {

    private static final long serialVersionUID = 1954787389313156606L;

    public VibiException(String format, Object ... arguments) {
        super(String.format(format, arguments));
    }

    public VibiException(Throwable cause, String format, Object ... arguments) {
        super(String.format(format, arguments), cause);
    }
}
