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

package co.com.elis.core.document.allowance;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import co.com.elis.core.document.validation.ExportationValidationGroup;
import co.com.elis.core.util.ResourceInterpolator;
import java.math.BigDecimal;

public class AllowanceCharge {

    @Getter
    private final boolean chargeIndicator;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ALLOWANCE_REASON_EXPORT", groups = ExportationValidationGroup.class)
    private String reason;

    @Getter
    private BigDecimal percentage;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ALLOWANCE_AMOUNT")
    private final BigDecimal amount;

    public AllowanceCharge(boolean chargeIndicator, BigDecimal amount) {
        this.chargeIndicator = chargeIndicator;
        this.amount = amount;
    }

    public AllowanceCharge(boolean chargeIndicator, BigDecimal amount, String reason) {
        this.chargeIndicator = chargeIndicator;
        this.reason = reason;
        this.amount = amount;
    }

    public AllowanceCharge(boolean chargeIndicator, String reason, BigDecimal percentage, BigDecimal amount) {
        this.chargeIndicator = chargeIndicator;
        this.reason = reason;
        this.percentage = percentage;
        this.amount = amount;
    }

    public BigDecimal applyTo(BigDecimal value) {
        if (isChargeIndicator()) {
            return value.add(getAmount());
        } else {
            return value.subtract(getAmount());
        }
    }

    public Set<ConstraintViolation<AllowanceCharge>> validateForExportation() {
        Validator validator = Validation.buildDefaultValidatorFactory()
                .usingContext()
                .messageInterpolator(new ResourceInterpolator())
                .getValidator();
        return validator.validate(this, ExportationValidationGroup.class);
    }

}
