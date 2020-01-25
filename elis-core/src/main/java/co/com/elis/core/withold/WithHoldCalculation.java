package co.com.elis.core.withold;

import co.com.elis.core.tax.Tax;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class WithHoldCalculation {

    @NotNull(message = "ELIS_CORE_VAL_TAX_CALC_TAX_PERCENTAGE")
    private final BigDecimal percentage;

    @NotNull
    private final WithHoldType withHoldType;

    private WithHoldCalculation(BigDecimal percentage, WithHoldType taxType) {
        this.percentage = percentage;
        this.withHoldType = taxType;
    }

    public WithHold applyTo(Tax tax) throws ElisCoreException {

        tax.setWithHolded(true);

        BigDecimal withHoldTotal = tax.getTaxTotal().multiply(percentage)
                .setScale(4, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

        return WithHold.createAs(withHoldType)
                .withPercentage(percentage)
                .withTotal(withHoldTotal)
                .build();
    }

    public WithHold applyTo(BigDecimal calcTotal) {
        BigDecimal withHoldTotal = calcTotal.multiply(percentage)
                .setScale(4, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

        return WithHold.createAs(withHoldType)
                .withPercentage(percentage)
                .withTotal(withHoldTotal)
                .build();
    }

    public static WithHoldCalculationBuilder of(WithHoldType type) {
        return new WithHoldCalculationBuilder(type);
    }

    @RequiredArgsConstructor
    public static class WithHoldCalculationBuilder {

        private BigDecimal percentage;

        private final WithHoldType type;

        public WithHoldCalculationBuilder withPercentage(BigDecimal percentage) {
            this.percentage = percentage;
            return this;
        }

        public WithHoldCalculationBuilder withPercentage(float percentage) {
            return withPercentage(BigDecimal.valueOf(percentage));
        }

        public WithHoldCalculation build() {
            return new WithHoldCalculation(percentage, type);
        }

    }

}
