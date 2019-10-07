package co.com.elis.core.software;

import lombok.Getter;

public enum Environment {

    PRODUCTION(1, "https://catalogo-vpfe.dian.gov.co/document/searchqr?documentkey="),
    HABILITATION(2, "https://catalogo-vpfe-hab.dian.gov.co/document/searchqr?documentkey=");

    private final Integer code;

    @Getter
    private final String qrUrl;

    private Environment(Integer code, String qrUrl) {
        this.code = code;
        this.qrUrl = qrUrl;
    }

    @Override
    public String toString() {
        return String.format("%01d", code);
    }

}
