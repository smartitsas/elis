package co.com.elis.core.person;

public enum Regime {

    REGIMEN_SIMPLIFICADO(0),
    REGIMEN_COMUN(2),
    NO_APLICABLE(3);

    private final int code;

    private Regime(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("%02d", code);
    }

}
