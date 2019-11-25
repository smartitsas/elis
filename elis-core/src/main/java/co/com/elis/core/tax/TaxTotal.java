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

import co.com.elis.core.tax.validation.ConsistentTaxTotal;
import co.com.elis.core.tax.validation.ConsistentTaxType;
import static co.com.elis.core.util.DecimalUtils.scaledOrNull;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@ConsistentTaxType
@ConsistentTaxTotal
public class TaxTotal implements Iterable<Tax> {

    @Getter
    @NotNull
    private final BigDecimal taxAmount;

    @Getter
    private final BigDecimal taxableAmount;

    @Getter
    private final TaxType type;

    //TODO: When calculating an ITEM there is no way to know if is retained, we should check this
    @Getter
    private final boolean isRetained;

    @Getter
    @Size(min = 1, message = "ELIS_CORE_VAL_TAX_SUBTAXES")
    @NotNull(message = "ELIS_CORE_VAL_TAX_SUBTAXES")
    private final List<Tax> taxSubtotals;

    /**
     * Constructor used to create a tax total with already calculated data
     *
     * @param taxAmount
     * @param type
     * @param isRetained if this tax is retained
     * @param taxSubtotals
     */
    public TaxTotal(TaxType type, BigDecimal taxAmount, boolean isRetained, List<Tax> taxSubtotals) {
        this.taxAmount = scaledOrNull(taxAmount);
        this.type = type;
        this.isRetained = isRetained;
        this.taxSubtotals = taxSubtotals;
        this.taxableAmount = this.taxSubtotals.get(0).getTaxableAmount();
    }

    /**
     * Constructor used to create a tax total with already calculated data
     *
     * @param taxAmount
     * @param type
     * @param taxSubtotals
     */
    public TaxTotal(TaxType type, BigDecimal taxAmount, List<Tax> taxSubtotals) {
        this(type, taxAmount, false, taxSubtotals);
    }

    /**
     * Constructor used to create a tax total calculating internal values
     *
     * @param taxSubtotals
     * @throws co.com.elis.exception.ElisCoreException
     */
    public TaxTotal(List<Tax> taxSubtotals) throws ElisCoreException {
        this(taxSubtotals, false);
    }

    /**
     * Constructor used to create a tax total calculating internal values
     *
     * @param taxSubtotals
     * @param isRetained
     * @throws co.com.elis.exception.ElisCoreException
     */
    public TaxTotal(List<Tax> taxSubtotals, boolean isRetained) throws ElisCoreException {
        if (taxSubtotals.isEmpty()) {
            throw new ElisCoreException("Subtotal list must not be empty");
        }
        Tax subtotal = taxSubtotals.get(0);
        this.taxAmount = taxSubtotals.stream().map(Tax::getTaxTotal).reduce((a, b) -> a.add(b)).orElseThrow(() -> new ElisCoreException("Could not calculate total from subtotals"));
        this.type = subtotal.getType();
        this.taxSubtotals = taxSubtotals;
        this.isRetained = isRetained;
        this.taxableAmount = this.taxSubtotals.get(0).getTaxableAmount();
    }

    public void validate() throws ElisCoreException {
        BigDecimal sum = BigDecimal.ZERO;
        TaxType firstType = taxSubtotals.get(0).getType();

        for (Tax taxSubtotal : taxSubtotals) {
            if (!taxSubtotal.getType().equals(firstType)) {
                throw new ElisCoreException("El tipo del subtotal: " + taxSubtotal.getType() + " no coincide con el de el total: " + firstType);
            }
            sum = sum.add(taxSubtotal.getTaxTotal());
        }

        if (sum.compareTo(taxAmount) != 0) {
            throw new ElisCoreException("Para el tipo: " + type + "El valor total: " + taxAmount + " no coincide con la suma de los subtotales: " + sum);
        }

    }

    public String toPlainString() {
        return taxAmount.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    @Override
    public Iterator<Tax> iterator() {
        return taxSubtotals.iterator();
    }

}
