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

import co.com.elis.core.tax.Tax;
import co.com.elis.core.tax.TaxTotal;
import co.com.elis.core.tax.TaxType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TaxTypeValidator implements ConstraintValidator<ConsistentTaxType, TaxTotal> {

    @Override
    public boolean isValid(TaxTotal taxTotal, ConstraintValidatorContext context) {
        TaxType firstType = taxTotal.getType();

        for (Tax taxSubtotal : taxTotal) {
            if (!taxSubtotal.getType().equals(firstType)) {
                return false;
            }
        }

        return true;
    }

}
