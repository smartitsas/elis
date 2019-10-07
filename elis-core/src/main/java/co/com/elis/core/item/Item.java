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
import co.com.elis.core.tax.validation.ValidateItemTax;
import static co.com.elis.core.util.DecimalUtils.scaledOrNull;
import co.com.elis.core.util.ResourceInterpolator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@ValidateItemTax
public class Item {

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ITEM_POS")
    private final Integer position;

    @Getter
    private final String code;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ITEM_DESC")
    private final String description;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ITEM_UNITS")
    private final String units;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ITEM_TOTAL")
    private final BigDecimal total;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ITEM_QUANTITY")
    private final BigDecimal quantity;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ITEM_UNITARYVAL")
    private final BigDecimal unitaryValue;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ITEM_TAXLIST")
    private final List<Tax> taxes;

    public Item(Integer position, String code, String description, String units, BigDecimal total, BigDecimal quantity, BigDecimal unitaryValue, List<Tax> taxCollection) {
        this.position = position;
        this.description = description;
        this.total = scaledOrNull(total);
        this.quantity = scaledOrNull(quantity);
        this.unitaryValue = scaledOrNull(unitaryValue);
        this.taxes = taxCollection;
        this.code = code;
        this.units = units;
    }

    public Tax getTax(TaxType taxType) {
        return taxes.stream()
                .filter(t -> t.getType() == taxType)
                .findFirst()
                .orElse(Tax.createWithZeros(taxType).withTaxableAmount(total).build());
    }

    public boolean isFreeOfCharge() {
        return BigDecimal.ZERO.compareTo(total) == 0;
    }

    public Set<ConstraintViolation<Item>> validate() {
        Validator validator = Validation.buildDefaultValidatorFactory()
                .usingContext()
                .messageInterpolator(new ResourceInterpolator())
                .getValidator();
        return validator.validate(this);
    }

    public String toDescriptiveId() {
        StringBuilder sb = new StringBuilder();

        if (position != null) {
            sb.append(position).append(". ");
        }
        if (code != null) {
            sb.append(code);
        }
        return sb.toString();
    }

}
