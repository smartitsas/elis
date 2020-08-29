package co.com.elis.core.document.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PaymentType {

    CASH(1),
    CREDIT(2);

    @Getter
    private final int code;

    static PaymentType valueOfCode(String value) {
        for (PaymentType type : PaymentType.values()) {
            if (type.getCode() == Integer.parseInt(value)) {
                return type;
            }
        }
        return null;
    }

}
