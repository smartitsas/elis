package co.com.elis.core.document.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PaymentType {

    CASH(1),
    CREDIT(2);

    @Getter
    private final int code;

}
