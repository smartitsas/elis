/** ********************************************************************************************
 *
 * ELectronic Invoicing System Community Core library
 * Copyright (C) 2017-2018. Smart IT S.A.S. <smartit.net.co>
 *
 * This file is licensed under the GNU Affero General Public License version 3
 * as published by the Free Software Foundation.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * You should have received a copy of the GNU Affero General Public License. If
 * not, please visit <http://www.gnu.org/licenses/agpl-3.0.html>.
 *
 *********************************************************************************************
 */
package co.com.elis.core.tax;

import static co.com.elis.core.util.DecimalUtils.scaledOrNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Tax {

    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final TaxType type;

    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final BigDecimal percentage;

    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final BigDecimal taxableAmount;

    @NotNull
    private final BigDecimal taxTotal;

    @Setter
    private boolean withHolded;

    public static final Tax ICA_ZERO = new Tax(TaxType.ICA, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    public static final Tax IVA_ZERO = new Tax(TaxType.IVA, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    public static final Tax CONSUMPTION_ZERO = new Tax(TaxType.CONSUMPTION, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * Create tax with type, percentage and total (this must be calculated by
     * the user)
     *
     * @param type Type of the Tax
     * @param percentage Percentage of the tax to be created user
     * @param taxableAmount Original amount to apply the percentage
     */
    private Tax(TaxType type, BigDecimal percentage, BigDecimal taxableAmount, BigDecimal taxTotal) {

        if (type == null) {
            throw new NullPointerException("type must not be null");
        }

        this.percentage = scaledOrNull(percentage, 2);
        this.type = type;
        this.taxableAmount = scaledOrNull(taxableAmount);
        this.taxTotal = scaledOrNull(taxTotal);
    }

    public Tax add(Tax otherSubtotal) {
        return new Tax(type, percentage, taxableAmount, taxTotal.add(otherSubtotal.getTaxTotal()));
    }

    public boolean isZero() {
        return BigDecimal.ZERO.compareTo(this.taxTotal) == 0;
    }

    /**
     * Creates a Builder for tax.
     *
     * Each unassigned value will NOT be ZERO by default
     *
     * @param type
     * @return
     */
    public static TaxBuilder createAs(TaxType type) {
        return new TaxBuilder(type, false);
    }

    /**
     * Creates a Builder for tax.
     *
     * Each unassigned value will be ZERO by default
     *
     * @param type
     * @return
     */
    public static TaxBuilder createWithZeros(TaxType type) {
        return new TaxBuilder(type, true);
    }

    public static class TaxBuilder {

        private final TaxType type;
        private BigDecimal percentage;
        private BigDecimal taxableAmount;
        private BigDecimal taxTotal;

        public TaxBuilder(TaxType type, boolean defaultToZero) {
            this.type = type;
            if (defaultToZero) {
                this.percentage = BigDecimal.ZERO.setScale(2);
                this.taxableAmount = BigDecimal.ZERO.setScale(4);
                this.taxTotal = BigDecimal.ZERO.setScale(4);
            }
        }

        public TaxBuilder withPercentage(BigDecimal percentage) {
            this.percentage = percentage;
            return this;
        }

        public TaxBuilder withPercentage(double percentage) {
            this.percentage = BigDecimal.valueOf(percentage).setScale(4, RoundingMode.HALF_UP);
            return this;
        }

        public TaxBuilder withTaxableAmount(BigDecimal taxableAmount) {
            this.taxableAmount = taxableAmount;
            return this;
        }

        public TaxBuilder withTaxTotal(BigDecimal taxTotal) {
            this.taxTotal = taxTotal;
            return this;
        }

        public Tax build() {
            return new Tax(type, percentage, taxableAmount, taxTotal);
        }

    }

}
