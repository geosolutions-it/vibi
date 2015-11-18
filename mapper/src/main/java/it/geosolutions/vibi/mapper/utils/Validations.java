package it.geosolutions.vibi.mapper.utils;

public final class Validations {

    private Validations() {
    }

    public static <T> T checkNotNull(T reference, String errorMessage, Object... arguments) {
        if (reference == null) {
            throw new RuntimeException(String.format(errorMessage, arguments));
        }
        return reference;
    }

    public static void checkCondition(boolean condition, String errorMessage, Object... arguments) {
        if (!condition) {
            throw new RuntimeException(String.format(errorMessage, arguments));
        }
    }
}
