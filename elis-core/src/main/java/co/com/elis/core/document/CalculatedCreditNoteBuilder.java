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

import co.com.elis.core.item.CreditNoteDiscrepancyReason;
import co.com.elis.core.item.Discrepancy;
import co.com.elis.core.software.Software;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.exception.ElisCoreException;
import java.util.List;
import java.util.stream.Collectors;

public class CalculatedCreditNoteBuilder extends AbstractNoteBuilder<CalculatedCreditNoteBuilder.CalculatedMandatoryContext> {

    private CreditNoteDiscrepancyReason discrepancyReason;

    public CalculatedCreditNoteBuilder(Software software) {
        super(software);
    }

    public CreditNote getCalculatedResult() throws ElisCoreException {
        OtherRelatedData otherData = new OtherRelatedData(delivery, paymentDataBuilder.buildOrNull(), exchangeRate, referenceList, additionalNotes);
        TaxTotalList taxTotalList = TaxTotalList.buildTotalList(true, false, itemList.getTaxes());
        MonetaryTotal legalMonetaryTotal = calculateTotals(itemList, taxTotalList, allowanceCharge);
        DocumentNumber documentNumber = new DocumentNumber(prefix, consecutive);

        List<AffectedInvoice> affectedInvoices = itemList.stream().flatMap(t -> t.getAffectedInvoices().stream()).collect(Collectors.toList());

        Discrepancy discrepancy = new Discrepancy(affectedInvoices, discrepancyReason);

        CreditNote creditNote = new CreditNote(
                new Header(supplierParty, software, receiverParty, invoiceDate, documentNumber),
                taxTotalList,
                legalMonetaryTotal,
                itemList,
                otherData,
                discrepancy
        );

        creditNote.validateOrThrow();
        return creditNote;
    }

    @Override
    public CalculatedMandatoryContext withinMandatorySection() {
        return new CalculatedMandatoryContext(this);
    }

    public CalculatedOptionalContext withinOptionalSection() {
        return new CalculatedOptionalContext(this);
    }

    public static class CalculatedMandatoryContext extends AbstractNoteBuilder.MandatoryContext<CalculatedMandatoryContext, CalculatedOptionalContext> {

        public CalculatedMandatoryContext(CalculatedCreditNoteBuilder invoiceBuilder) {
            super(invoiceBuilder);
        }

        @Override
        public CalculatedOptionalContext withinOptionalSection() {
            return new CalculatedOptionalContext((CalculatedCreditNoteBuilder) builder);
        }

        public CreditNote getResult() throws ElisCoreException {
            CalculatedCreditNoteBuilder creditNoteBuilder = (CalculatedCreditNoteBuilder) builder;

            return creditNoteBuilder.getCalculatedResult();
        }

        @Override
        protected CalculatedMandatoryContext collectContext() {
            return this;
        }

    }

    public static class CalculatedOptionalContext extends AbstractNoteBuilder.OptionalContext<CalculatedMandatoryContext, CalculatedOptionalContext> {

        private CalculatedOptionalContext(CalculatedCreditNoteBuilder builder) {
            super(builder);
        }

        public CalculatedOptionalContext setDiscrepancyReason(CreditNoteDiscrepancyReason reason) {
            ((CalculatedCreditNoteBuilder) builder).discrepancyReason = reason;
            return collectContext();
        }

        public CreditNote getResult() throws ElisCoreException {
            CalculatedCreditNoteBuilder creditNoteBuilder = (CalculatedCreditNoteBuilder) builder;
            return creditNoteBuilder.getCalculatedResult();
        }

        @Override
        protected CalculatedOptionalContext collectContext() {
            return this;
        }

    }
}
