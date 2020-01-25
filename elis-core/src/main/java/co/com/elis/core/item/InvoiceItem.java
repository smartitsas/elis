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

package co.com.elis.core.item;

import co.com.elis.core.tax.Tax;
import co.com.elis.core.withold.WithHold;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class InvoiceItem extends Item {
    
    @Getter
    @NotNull(message = "ELIS_CORE_VAL_ITEM_PAYMENT_TERMS")
    private final List<String> notes;    

    InvoiceItem(Integer id, String code, String description, String units, BigDecimal quantity, BigDecimal unitaryValue, BigDecimal total, List<Tax> taxList, List<WithHold> withHolds, List<String> notes) {
        super(id, code, description, units, total, quantity, unitaryValue, taxList, withHolds);
        this.notes = notes;
    }

    public static ItemPopulationBuilder populateAs() {
        return new ItemPopulationBuilder();
    }

    public static ItemCalculationBuilder calculateAs() {
        return new ItemCalculationBuilder();
    }


}
