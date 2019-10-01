package co.com.elis.core.software;

public enum Environment {

    PRODUCTION(1),
    HABILITATION(2);

    private final Integer code;

    private Environment(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("%01d", code);
    }

}
