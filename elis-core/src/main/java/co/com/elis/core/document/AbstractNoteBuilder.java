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
package co.com.elis.core.document;

import co.com.elis.core.document.allowance.AllowanceCharge;
import co.com.elis.core.document.builder.AbstractBuilder;
import co.com.elis.core.document.builder.MandatoryBuildContext;
import co.com.elis.core.document.builder.OptionalBuildContext;
import co.com.elis.core.item.Item;
import co.com.elis.core.item.ItemList;
import co.com.elis.core.item.NoteItem;
import co.com.elis.core.software.Software;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.core.withold.WithHoldingList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNoteBuilder<M extends MandatoryBuildContext> extends AbstractBuilder<M, NoteItem> {

    protected String prefix;

    protected Long consecutive;

    protected List<AffectedInvoice> affectedInvoices;

    public AbstractNoteBuilder(Software software) {
        super(software);
        affectedInvoices = new ArrayList<>();
    }

    protected MonetaryTotal calculateTotals(ItemList<? extends Item> itemList, TaxTotalList taxTotalList, List<AllowanceCharge> allowanceCharge) {
        BigDecimal lineTotal = itemList.getCalculatedTotal();
        BigDecimal taxableAmount = taxTotalList.getCalculatedTaxableAmount();
        BigDecimal taxAmount = taxTotalList.getCalculatedTaxAmount();
        BigDecimal withHoldingTotal = WithHoldingList.buildWithHoldingList(itemList.getWithHolds()).getWithHoldingTotal();

        return new MonetaryTotal(currency, lineTotal, taxableAmount, taxAmount, withHoldingTotal, allowanceCharge);
    }

    public abstract static class MandatoryContext<M extends MandatoryBuildContext, O extends OptionalBuildContext> extends MandatoryBuildContext<M, O, NoteItem> {

        public MandatoryContext(AbstractNoteBuilder invoiceBuilder) {
            super(invoiceBuilder);
        }

        public M setPrefix(String prefix) {
            ((AbstractNoteBuilder) builder).prefix = prefix;
            return collectContext();
        }

        public M setConsecutive(Long consecutive) {
            ((AbstractNoteBuilder) builder).consecutive = consecutive;
            return collectContext();
        }

        public M addAffectedInvoice(AffectedInvoice affectedInvoice) {
            AbstractNoteBuilder creditNoteBuilder = (AbstractNoteBuilder) builder;
            creditNoteBuilder.affectedInvoices.add(affectedInvoice);
            return collectContext();
        }

        public M addAffectedInvoices(List<AffectedInvoice> affectedInvoices) {
            AbstractNoteBuilder castedBuilder = (AbstractNoteBuilder) builder;
            castedBuilder.affectedInvoices.addAll(affectedInvoices);
            return collectContext();
        }

    }

    public abstract static class OptionalContext<M extends MandatoryBuildContext, O extends OptionalBuildContext> extends OptionalBuildContext<M, O, NoteItem> {

        protected OptionalContext(AbstractNoteBuilder<M> builder) {
            super(builder);
        }

    }
}
