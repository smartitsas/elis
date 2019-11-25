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

import co.com.elis.core.item.AbstractItemBuilder.MandatoryContext;
import co.com.elis.core.tax.Tax;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NoteItemPopulationBuilder extends AbstractItemBuilder {

    private BigDecimal total;

    private final List<Tax> taxList;

    public NoteItemPopulationBuilder() {
        this.taxList = new ArrayList<>();
    }

    private NoteItem get() throws ElisCoreException {
        NoteItem item = new NoteItem(position, code, description, units, quantity, unitaryValue, total, taxList);

        validateOrThrow(item);
        return item;
    }

    public PopulationMandatoryContext withinMandatorySection() {
        return new PopulationMandatoryContext(this);
    }

    public static class PopulationMandatoryContext extends MandatoryContext<NoteItemPopulationBuilder, PopulationMandatoryContext> {

        public PopulationMandatoryContext(NoteItemPopulationBuilder builder) {
            super(builder);
        }

        /**
         * Assigns position of the item
         *
         * @param position Id or position of the item
         * @return Chaining Builder
         */
        @Override
        public PopulationMandatoryContext setPosition(Integer position) {
            builder.position = position;
            return this;
        }

        public PopulationMandatoryContext setTotal(BigDecimal total) {
            builder.total = total;
            return this;
        }

        public PopulationMandatoryContext setTotal(double total) {
            builder.total = BigDecimal.valueOf(total);
            return this;
        }

        public PopulationOptionalContext withinOptionalSection() {
            return new PopulationOptionalContext(builder);
        }

        public NoteItem get() throws ElisCoreException {
            return builder.get();
        }

        @Override
        protected PopulationMandatoryContext collectContext() {
            return this;
        }

    }

    public static class PopulationOptionalContext extends OptionalContext<NoteItemPopulationBuilder, PopulationOptionalContext> {

        public PopulationOptionalContext(NoteItemPopulationBuilder builder) {
            super(builder);
        }

        public NoteItem get() throws ElisCoreException {
            return builder.get();
        }

        public PopulationOptionalContext addTax(Tax tax) {
            builder.taxList.add(tax);
            return this;
        }

        public PopulationOptionalContext addTaxes(List<Tax> taxes) {
            builder.taxList.addAll(taxes);
            return this;
        }

        @Override
        protected PopulationOptionalContext collectContext() {
            return this;
        }

    }

}
