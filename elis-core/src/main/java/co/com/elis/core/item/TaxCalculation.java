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
package co.com.elis.core.item;

import co.com.elis.core.tax.Tax;
import co.com.elis.core.tax.TaxType;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.validation.constraints.NotNull;

public class TaxCalculation {

    @NotNull(message = "ELIS_CORE_VAL_TAX_CALC_TAX_TYPE")
    private final TaxType type;

    @NotNull(message = "ELIS_CORE_VAL_TAX_CALC_TAX_PERCENTAGE")
    private final BigDecimal percentage;

    private TaxCalculation(TaxType type, BigDecimal percentage) {
        this.type = type;
        this.percentage = percentage;
    }

    public Tax applyTo(BigDecimal calcTotal) throws ElisCoreException {
        BigDecimal taxTotal = calcTotal.multiply(percentage)
                .setScale(4, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

        Tax tax = Tax.createAs(type)
                .withPercentage(percentage)
                .withTaxTotal(taxTotal)
                .withTaxableAmount(calcTotal)
                .build();

        return tax;
    }

    public static TaxCalculationBuilder of(TaxType type) {
        return new TaxCalculationBuilder(type);
    }

    public static class TaxCalculationBuilder {

        private final TaxType type;

        private BigDecimal percentage;

        private TaxCalculationBuilder(TaxType type) {
            this.type = type;
        }

        public TaxCalculationBuilder withPercentage(BigDecimal percentage) {
            this.percentage = percentage;
            return this;
        }

        public TaxCalculationBuilder withPercentage(float percentage) {
            return withPercentage(BigDecimal.valueOf(percentage));
        }

        public TaxCalculation build() {
            return new TaxCalculation(type, percentage);
        }

    }

}
