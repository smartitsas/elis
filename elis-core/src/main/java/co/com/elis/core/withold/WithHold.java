package co.com.elis.core.withold;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithHold {

    private final WithHoldType withHoldType;

    private final BigDecimal percentage;

    private final BigDecimal retainableAmount;

    private final BigDecimal withHoldtotal;

    public static WithHoldBuilder createAs(WithHoldType type) {
        return new WithHoldBuilder(type);
    }

    public static class WithHoldBuilder {

        private final WithHoldType type;
        private BigDecimal percentage;
        private BigDecimal withHoldtotal;
        private BigDecimal retainableAmount;

        public WithHoldBuilder(WithHoldType type) {
            this.type = type;
        }

        public WithHoldBuilder withPercentage(BigDecimal percentage) {
            this.percentage = percentage;
            return this;
        }

        public WithHoldBuilder withPercentage(double percentage) {
            this.percentage = BigDecimal.valueOf(percentage).setScale(4, RoundingMode.HALF_UP);
            return this;
        }

        public WithHoldBuilder withRetainableAmount(BigDecimal retainableAmount) {
            this.retainableAmount = retainableAmount;
            return this;
        }

        public WithHoldBuilder withRetainableAmount(double retainableAmount) {
            this.retainableAmount = BigDecimal.valueOf(retainableAmount).setScale(4, RoundingMode.HALF_UP);
            return this;
        }

        public WithHoldBuilder withTotal(BigDecimal withHoldTotal) {
            this.withHoldtotal = withHoldTotal;
            return this;
        }

        public WithHoldBuilder withTotal(double totalValue) {
            this.withHoldtotal = BigDecimal.valueOf(totalValue).setScale(4, RoundingMode.HALF_UP);
            return this;
        }

        public WithHold build() {

            if (withHoldtotal == null) {
                withHoldtotal = BigDecimal.ZERO;
            }

            if (percentage == null) {
                percentage = BigDecimal.ZERO;
            }

            if (retainableAmount == null) {
                retainableAmount = BigDecimal.ZERO;
            }

            return new WithHold(type, percentage, retainableAmount, withHoldtotal);
        }

    }

}
