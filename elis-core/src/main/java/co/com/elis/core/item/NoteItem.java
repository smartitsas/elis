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
import java.math.BigDecimal;
import java.util.List;

public class NoteItem extends Item {

    NoteItem(Integer id, String code, String description, String units, BigDecimal quantity, BigDecimal unitaryValue, BigDecimal total, List<Tax> taxList) {
        super(id, code, description, units, total, quantity, unitaryValue, taxList);
    }

    public static NoteItemPopulationBuilder populateAs() {
        return new NoteItemPopulationBuilder();
    }

    public static NoteItemCalculationBuilder calculateAs() {
        return new NoteItemCalculationBuilder();
    }

}
