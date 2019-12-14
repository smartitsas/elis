package co.com.elis.core.document.payment;

import java.util.Objects;
import java.util.Optional;
import lombok.Getter;

public enum PaymentChannel {

    NOT_DEFINED(1, "No definido"),
    CREDIT_ACH(2, "Crédito ACH"),
    DEBIT_ACH(3, "Débito ACH"),
    REVERSED_DEBIT_ACH(4, "Reversión débito de demanda ACH"),
    REVERSED_CREDIT_ACH(5, "Reversión crédito de demanda ACH"),
    CREDIT_REQUESTED_ACH(6, "Crédito de demanda ACH"),
    DEBIT_REQUESTED_ACH(7, "Débito de demanda ACH"),
    KEEP(8, "Mantener"),
    NATIONAL_CLEARING(9, "Clearing nacional o regional"),
    CASH(10, "Efectivo"),
    REVERSION_CREDIT_SAVING(11, "Reversión crédito ahorro"),
    REVERSION_DEBIT_SAVING(12, "Reversión débito ahorro"),
    CREDIT_SAVING(13, "Crédito ahorro"),
    DEBIT_SAVING(14, "Débito ahorro"),
    CREDIT_BOOKENTRY(15, "Bookentry crédito"),
    DEBIT_BOOKENTRY(16, "Bookentry Débito"),
    //TODO: Complete

    CHEQUE(20, "Cheque"),
    BANCK_CHEQUE(23, "Cheque bancario"),
    CREDIT_BUSINESS_CORPORATE_INTERCHANGE(39, "Crédito Negocio Intercambio Corporativo (CTX)"),
    DEBIT_BUSINESS_CORPORATE_INTERCHANGE(40, "Débito Negocio Intercambio Corporativo (CTX)"),
    CONCENTRATION_CASH_OR_PLUS_CREDIT(41, "Concentración efectivo/Desembolso Crédito plus (CCD+)"),
    BANK_DEPOSIT(42, "Consiganción bancaria"),
    CONCENTRATION_CASH_OR_PLUS_DEBIT(43, "Concentración efectivo / Desembolso Débito plus (CCD+)"),
    EXCHANGE_NOTE(44, "Nota cambiaria"),
    TRANSFER_BANK_CREDIT(45, "Transferencia Crédito Bancario"),
    TRANSFER_INTERBANK_DEBIT(46, "Transferencia Débito Interbancario"),
    TRANSFER_BANK_DEBIT(47, "Transferencia Débito Bancaria"),
    CREDIT_CARD(48, "Tarjeta Crédito"),
    DEBIT_CARD(49, "Tarjeta Débito"),
    POST_MONEY_TRANSFER(50, "Postgiro"),
    FRENCH_BANK_TELEX_STANDARD(51, "Telex estándar bancario francés");

    @Getter
    private final Integer code;
    @Getter
    private final String description;

    private PaymentChannel(Integer code, String description) {
        this.code = code;
        this.description = description;
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
