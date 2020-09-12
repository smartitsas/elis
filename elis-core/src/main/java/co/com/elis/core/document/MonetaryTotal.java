/**********************************************************************************************
 *
 * ELectronic Invoicing System Community Core library
 * Copyright (C) 2017-2018. Smart IT S.A.S. <smartit.net.co>
 *
 * This file is licensed under the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * You should have received a copy of the GNU Affero General Public License.  If not, please
 * visit <http://www.gnu.org/licenses/agpl-3.0.html>.
 *
 **********************************************************************************************/

package co.com.elis.core.document;

import co.com.elis.core.document.allowance.AllowanceCharge;
import static co.com.elis.core.util.DecimalUtils.scaledOrNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class MonetaryTotal {

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_TOTAL")
    private final BigDecimal lineTotal;

    @Getter
    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final BigDecimal taxableAmount;
    
    
    @Getter
    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final BigDecimal taxAmount;    
    
    @Getter
    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final BigDecimal taxInclusiveAmount;    

    @Getter
    @NotNull(message = "ELIS_CORE_UNKNOWN")
    private final BigDecimal payableAmount;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_TOTAL_CURRENCY")
    private final String currency;

    @Getter
    private final BigDecimal chargeTotal;

    @Getter
    private final BigDecimal discountTotal;

    public MonetaryTotal(String currency, BigDecimal lineTotal, BigDecimal taxableAmount, BigDecimal taxAmount, BigDecimal payableAmount) {
        this.currency = currency;
        this.lineTotal = scaledOrNull(lineTotal, 2);
        this.taxableAmount = scaledOrNull(taxableAmount);
        this.taxAmount = taxAmount;
        this.taxInclusiveAmount = taxAmount.add(lineTotal);
        this.chargeTotal = scaledOrNull(BigDecimal.ZERO);
        this.discountTotal = scaledOrNull(BigDecimal.ZERO);
        this.payableAmount = scaledOrNull(payableAmount, 2);
    }

    public MonetaryTotal(String currency, BigDecimal lineTotal, BigDecimal taxableAmount, BigDecimal taxAmount, BigDecimal payableAmount, BigDecimal chargeTotal, BigDecimal discountTotal) {
        this.currency = currency;
        this.lineTotal = scaledOrNull(lineTotal, 2);
        this.taxableAmount = scaledOrNull(taxableAmount);
        this.taxAmount = taxAmount;
        this.taxInclusiveAmount = taxAmount.add(lineTotal);
        this.chargeTotal = scaledOrNull(chargeTotal);
        this.discountTotal = scaledOrNull(discountTotal);
        this.payableAmount = scaledOrNull(payableAmount, 2);
    }

    MonetaryTotal(String currency, BigDecimal lineTotal, BigDecimal taxableAmount, BigDecimal taxAmount, BigDecimal withHoldingTotal, List<AllowanceCharge> allowanceCharges) {
        this.currency = currency;
        this.lineTotal = scaledOrNull(lineTotal, 2);
        this.taxableAmount = scaledOrNull(taxableAmount, 2);
        this.taxAmount = taxAmount;
        
        BigDecimal chargeSum = BigDecimal.ZERO;
        BigDecimal discuntSum = BigDecimal.ZERO;

        for (AllowanceCharge allowanceCharge : allowanceCharges) {
            if (allowanceCharge.isChargeIndicator()) {
                chargeSum = chargeSum.add(allowanceCharge.getAmount());
            } else {
                discuntSum = discuntSum.add(allowanceCharge.getAmount());
            }
        }

        this.chargeTotal = scaledOrNull(chargeSum);
        this.discountTotal = scaledOrNull(discuntSum);
        this.taxInclusiveAmount = taxAmount.add(lineTotal);
        this.payableAmount = lineTotal.add(taxAmount).add(chargeSum).subtract(discuntSum).subtract(withHoldingTotal).setScale(2, RoundingMode.HALF_UP);
    }

    public String toPlainString() {
        return lineTotal.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

}
