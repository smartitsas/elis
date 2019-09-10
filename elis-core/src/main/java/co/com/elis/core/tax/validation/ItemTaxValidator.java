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

package co.com.elis.core.tax.validation;

import co.com.elis.core.item.Item;
import co.com.elis.core.tax.Tax;
import static co.com.elis.core.util.DecimalUtils.scaledOrNull;
import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ItemTaxValidator implements ConstraintValidator<ValidateItemTax, Item> {

    @Override
    public boolean isValid(Item item, ConstraintValidatorContext context) {
        boolean isValid = true;
        BigDecimal total = item.getTotal();

        for (Tax tax : item.getTaxes()) {
            BigDecimal calTax = scaledOrNull(tax.getPercentage().multiply(total).divide(BigDecimal.valueOf(100)));
            isValid &= calTax.compareTo(scaledOrNull(tax.getTaxTotal())) == 0;
            isValid &= total.compareTo(tax.getTaxableAmount()) == 0;
            isValid &= total.add(calTax).compareTo(tax.getTotal()) == 0;
        }

        return isValid;
    }

}
