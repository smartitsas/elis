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
import co.com.elis.core.withold.WithHold;
import co.com.elis.core.withold.WithHoldCalculation;
import co.com.elis.core.withold.WithHoldType;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemCalculationBuilder extends AbstractItemBuilder {

    private final Collection<TaxCalculation> taxCalcList = new ArrayList<>();
    private final Collection<WithHoldCalculation> withHoldCalcList = new ArrayList<>();
    private final List<String> paymentTerms = new ArrayList<>();

    private InvoiceItem getCalculatedResult() throws ElisCoreException {
        BigDecimal calcTotal = quantity.multiply(unitaryValue);
        List<Tax> taxList = new ArrayList<>(taxCalcList.size());
        List<WithHold> withHolds = new ArrayList<>();

        for (TaxCalculation taxCalculation : taxCalcList) {
            Tax tax = taxCalculation.applyTo(calcTotal);
            taxList.add(tax);
        }

        for (WithHoldCalculation withHoldCalculation : withHoldCalcList) {
            WithHold withHold;
            WithHoldType withHoldType = withHoldCalculation.getWithHoldType();

            if (!withHoldType.isOverTax()) {
                withHold = withHoldCalculation.applyTo(calcTotal);
            } else {
                Tax foundTax = taxList.stream()
                        .filter(tax -> tax.getType() == withHoldType.getTaxEquivalent())
                        .findAny()
                        .orElseThrow(() -> new ElisCoreException("There is no tax for withHold: " + withHoldType));

                withHold = withHoldCalculation.applyTo(foundTax);
            }

            withHolds.add(withHold);
        }

        InvoiceItem item = new InvoiceItem(position, code, description, units, quantity, unitaryValue, calcTotal, taxList, withHolds, paymentTerms);

        validateOrThrow(item);
        return item;
    }

    public CalculatedMandatoryContext withinMandatorySection() {
        return new CalculatedMandatoryContext(this);
    }

    public static class CalculatedMandatoryContext extends AbstractItemBuilder.MandatoryContext<ItemCalculationBuilder, CalculatedMandatoryContext> {

        public CalculatedMandatoryContext(ItemCalculationBuilder builder) {
            super(builder);
        }

        public CalculationOptionalContext withinOptionalSection() {
            return new CalculationOptionalContext(builder);
        }

        public InvoiceItem getCalculatedResult() throws ElisCoreException {
            return builder.getCalculatedResult();
        }

        @Override
        protected CalculatedMandatoryContext collectContext() {
            return this;
        }
    }

    public static class CalculationOptionalContext extends AbstractItemBuilder.OptionalContext<ItemCalculationBuilder, CalculationOptionalContext> {

        public CalculationOptionalContext(ItemCalculationBuilder builder) {
            super(builder);
        }

        public InvoiceItem getCalculatedResult() throws ElisCoreException {
            return builder.getCalculatedResult();
        }

        public CalculationOptionalContext addTax(TaxCalculation taxCalculation) {
            builder.taxCalcList.add(taxCalculation);
            return this;
        }

        @Override
        protected CalculationOptionalContext collectContext() {
            return this;
        }

        public CalculationOptionalContext addWithHolding(WithHoldCalculation withHold) {
            builder.withHoldCalcList.add(withHold);
            return this;
        }
    }

}
