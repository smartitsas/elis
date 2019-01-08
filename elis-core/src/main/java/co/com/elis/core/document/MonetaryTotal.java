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
    private final BigDecimal taxTotal;

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

    public MonetaryTotal(String currency, BigDecimal lineTotal, BigDecimal taxTotal, BigDecimal payableAmount) {
        this.currency = currency;
        this.lineTotal = lineTotal.setScale(4, RoundingMode.HALF_UP);
        this.taxTotal = taxTotal.setScale(4, RoundingMode.HALF_UP);
        this.payableAmount = payableAmount.setScale(4, RoundingMode.HALF_UP);
        this.chargeTotal = null;
        this.discountTotal = null;
    }

    public MonetaryTotal(String currency, BigDecimal lineTotal, BigDecimal taxTotal, BigDecimal payableAmount, BigDecimal chargeTotal, BigDecimal discountTotal) {
        this.currency = currency;
        this.lineTotal = lineTotal.setScale(4, RoundingMode.HALF_UP);
        this.taxTotal = taxTotal.setScale(4, RoundingMode.HALF_UP);
        this.payableAmount = payableAmount.setScale(4, RoundingMode.HALF_UP);
        this.chargeTotal = chargeTotal.setScale(4, RoundingMode.HALF_UP);
        this.discountTotal = discountTotal.setScale(4, RoundingMode.HALF_UP);
    }

    MonetaryTotal(String currency, BigDecimal lineTotal, BigDecimal taxTotal, List<AllowanceCharge> allowanceCharges) {
        this.currency = currency;
        this.lineTotal = lineTotal.setScale(4, RoundingMode.HALF_UP);
        this.taxTotal = taxTotal.setScale(4, RoundingMode.HALF_UP);
        BigDecimal chargeSum = BigDecimal.ZERO;
        BigDecimal discuntSum = BigDecimal.ZERO;

        for (AllowanceCharge allowanceCharge : allowanceCharges) {
            if (allowanceCharge.isChargeIndicator()) {
                chargeSum = chargeSum.add(allowanceCharge.getAmount());
            } else {
                discuntSum = discuntSum.add(allowanceCharge.getAmount());
            }
        }

        this.chargeTotal = chargeSum.setScale(4, RoundingMode.HALF_UP);
        this.discountTotal = discuntSum.setScale(4, RoundingMode.HALF_UP);
        this.payableAmount = lineTotal.add(taxTotal).add(chargeSum).subtract(discuntSum).setScale(4, RoundingMode.HALF_UP);
    }

    public String toPlainString() {
        return lineTotal.setScale(2, RoundingMode.DOWN).toPlainString();
    }

}
