package co.com.elis.core.document.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {

    CASH(1, "Efectivo"),
    CREDIT(2, "Crédito");

    private final int code;

    private final String description;

    static PaymentType valueOfCode(String value) {
        for (PaymentType type : PaymentType.values()) {
            if (type.getCode() == Integer.parseInt(value)) {
                return type;
            }
        }
        return null;
    }

}
