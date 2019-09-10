package co.com.elis.core.document.payment;

import java.util.Objects;
import java.util.Optional;
import lombok.Getter;

public enum PaymentChannel {

    NOT_DEFINED(1),
    CREDIT_ACH(2),
    DEBIT_ACH(3),
    REVERSED_DEBIT_ACH(4),
    REVERSED_CREDIT_ACH(5),
    CREDIT_REQUESTED_ACH(6),
    DEBIT_REQUESTED_ACH(7),
    KEEP(8),
    NATIONAL_CLEARING(9),
    CASH(10),
    REVERSION_CREDIT_SAVING(11),
    REVERSION_DEBIT_SAVING(12),
    CREDIT_SAVING(13),
    CREDIT_BUSINESS_CORPORATE_INTERCHANGE(39),
    DEBIT_BUSINESS_CORPORATE_INTERCHANGE(40),
    CONCENTRATION_CASH_OR_PLUS_CREDIT(41),
    BANK_DEPOSIT(42),
    CONCENTRATION_CASH_OR_PLUS_DEBIT(43),
    EXCHANGE_NOTE(44),
    TRANSFER_BANK_CREDIT(45),
    TRANSFER_INTERBANK_DEBIT(46),
    TRANSFER_BANK_DEBIT(47),
    CREDIT_CARD(48),
    DEBIT_CARD(49),
    POST_MONEY_TRANSFER(50),
    FRENCH_BANK_TELEX_STANDARD(51);

    @Getter
    private final Integer code;

    private PaymentChannel(Integer code) {
        this.code = code;
    }

    public static Optional<PaymentChannel> fromCode(Integer code) {

        for (PaymentChannel value : PaymentChannel.values()) {
            if (Objects.equals(value.code, code)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

}
