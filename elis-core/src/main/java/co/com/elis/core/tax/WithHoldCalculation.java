package co.com.elis.core.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.validation.constraints.NotNull;

public class WithHoldCalculation {

    @NotNull(message = "ELIS_CORE_VAL_TAX_CALC_TAX_TYPE")
    private final TaxType type;

    @NotNull(message = "ELIS_CORE_VAL_TAX_CALC_TAX_PERCENTAGE")
    private final BigDecimal percentage;

    private WithHoldCalculation(TaxType type, BigDecimal percentage) {
        this.type = type;
        this.percentage = percentage;
    }

    public Tax applyTo(BigDecimal calcTotal) {
        BigDecimal taxTotal = calcTotal.multiply(percentage)
                .setScale(4, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

        return Tax.createAs(type)
                .withPercentage(percentage)
                .withTaxTotal(taxTotal)
                .withTaxableAmount(calcTotal)
                .withTotal(taxTotal.add(calcTotal))
                .build();
    }

    public static TaxCalculationBuilder of(TaxType type) {
        return new TaxCalculationBuilder(type);
    }

    public static class TaxCalculationBuilder {

        private final TaxType type;

        private TaxCalculationBuilder(TaxType type) {
            this.type = type;
        }

        public WithHoldCalculation withPercentage(BigDecimal percentage) {
            return new WithHoldCalculation(type, percentage);
        }

        public WithHoldCalculation withPercentage(float percentage) {
            return withPercentage(BigDecimal.valueOf(percentage));
        }

    }
}
