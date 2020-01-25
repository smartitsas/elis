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

    RETE_IVA(true, TaxType.IVA),
    RETE_ICA(true, TaxType.ICA),
    RETE_FUENTE_RENTA(false, null);

    @Getter
    private final boolean overTax;

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
