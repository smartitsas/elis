/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.elis.core.withold;

import co.com.elis.core.tax.TaxType;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author andres
 */
@RequiredArgsConstructor
public enum WithHoldType {

    RETE_IVA(true, "05", "ReteIVA", TaxType.IVA),
    RETE_FUENTE_RENTA(false, "06", "ReteFuente", null),
    RETE_ICA(true, "07", "ReteICA", TaxType.ICA),
    FTO_HORTICULTURA(true, "20", "FtoHorticultura", null);

    @Getter
    private final boolean overTax;

    @Getter
    private final String code;

    @Getter
    private final String description;
    
    @Getter
    private final TaxType taxEquivalent;

    public static Optional<WithHoldType> forTaxType(TaxType type) {
        if (type == TaxType.IVA) {
            return Optional.of(RETE_IVA);
        } else if (type == TaxType.ICA) {
            return Optional.of(RETE_ICA);
        }
        return Optional.empty();
    }

}
