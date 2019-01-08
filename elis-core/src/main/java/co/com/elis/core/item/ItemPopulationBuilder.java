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

import co.com.elis.core.item.AbstractItemBuilder.MandatoryContext;
import co.com.elis.core.item.ItemPopulationBuilder.PopulationMandatoryContext;
import co.com.elis.core.item.ItemPopulationBuilder.PopulationOptionalContext;
import co.com.elis.core.tax.Tax;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ItemPopulationBuilder extends AbstractItemBuilder {

    private BigDecimal total;

    protected final List<Tax> taxList;

    private final List<String> paymentTerms = new ArrayList<>();

    public ItemPopulationBuilder() {
        this.taxList = new ArrayList<>();
    }

    private InvoiceItem get() throws ElisCoreException {
        InvoiceItem item = new InvoiceItem(position, code, description, quantity, unitaryValue, total, taxList, paymentTerms);

        validateOrThrow(item);
        return item;
    }

    public PopulationMandatoryContext withinMandatorySection() {
        return new PopulationMandatoryContext(this);
    }

    public static class PopulationMandatoryContext extends MandatoryContext<ItemPopulationBuilder, PopulationMandatoryContext> {

        public PopulationMandatoryContext(ItemPopulationBuilder builder) {
            super(builder);
        }

        public PopulationMandatoryContext setTotal(BigDecimal total) {
            builder.total = total;
            return this;
        }

        public PopulationMandatoryContext setTotal(double total) {
            builder.total = BigDecimal.valueOf(total).setScale(4, RoundingMode.HALF_UP);
            return this;
        }

        public PopulationOptionalContext withinOptionalSection() {
            return new PopulationOptionalContext(builder);
        }

        public InvoiceItem get() throws ElisCoreException {
            return builder.get();
        }

        @Override
        protected PopulationMandatoryContext collectContext() {
            return this;
        }

    }

    public static class PopulationOptionalContext extends OptionalContext<ItemPopulationBuilder, PopulationOptionalContext> {

        public PopulationOptionalContext(ItemPopulationBuilder builder) {
            super(builder);
        }

        public InvoiceItem get() throws ElisCoreException {
            return builder.get();
        }
        
        public PopulationOptionalContext addTax(Tax tax) {
            builder.taxList.add(tax);
            return this;
        }

        public PopulationOptionalContext addPaymentTerms(List<String> paymentTerms) {
            builder.paymentTerms.addAll(paymentTerms);
            return this;
        }

        public PopulationOptionalContext addPaymentTerm(String paymentTerm) {
            builder.paymentTerms.add(paymentTerm);
            return this;
        }

        @Override
        protected PopulationOptionalContext collectContext() {
            return this;
        }

    }

}
