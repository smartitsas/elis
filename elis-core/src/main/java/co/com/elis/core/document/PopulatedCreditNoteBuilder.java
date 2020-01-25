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

import co.com.elis.core.item.CreditNoteDiscrepancyReason;
import co.com.elis.core.item.Discrepancy;
import co.com.elis.core.software.Software;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.core.withold.WithHoldingList;
import co.com.elis.exception.ElisCoreException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PopulatedCreditNoteBuilder extends AbstractNoteBuilder<PopulatedCreditNoteBuilder.PopulatedMandatoryContext> {

    private TaxTotalList taxTotalList;

    private WithHoldingList withHoldList;

    private MonetaryTotal legalMonetaryTotal;

    private DocumentNumber documentNumber;

    private boolean validate;

    private CreditNoteDiscrepancyReason discrepancyReason;

    private boolean calculateCUDE;

    private String cude;

    public PopulatedCreditNoteBuilder(Software software) {
        super(software);
        this.affectedInvoices = new ArrayList<>();
        this.validate = true;
        this.withHoldList = new WithHoldingList(Collections.emptyList());
    }

    public CreditNote getResult() throws ElisCoreException {
        OtherRelatedData otherData = new OtherRelatedData(delivery, paymentDataBuilder.buildOrNull(), exchangeRate, referenceList, additionalNotes);

        Discrepancy discrepancy = new Discrepancy(affectedInvoices, discrepancyReason);

        CreditNote creditNote = new CreditNote(
                new Header(supplierParty, software, receiverParty, invoiceDate, documentNumber),
                taxTotalList,
                withHoldList,
                legalMonetaryTotal,
                itemList,
                otherData,
                discrepancy
        );

        if (calculateCUDE) {
            creditNote.calculateCude();
        } else {
            creditNote.setCude(cude);
        }

        if (validate) {
            creditNote.validateOrThrow();
        }
        return creditNote;
    }

    @Override
    public PopulatedMandatoryContext withinMandatorySection() {
        return new PopulatedMandatoryContext(this);
    }

    public PopulatedOptionalContext withinOptionalSection() {
        return new PopulatedOptionalContext(this);
    }

    public static class PopulatedMandatoryContext extends AbstractNoteBuilder.MandatoryContext<PopulatedMandatoryContext, PopulatedOptionalContext> {

        public PopulatedMandatoryContext(PopulatedCreditNoteBuilder invoiceBuilder) {
            super(invoiceBuilder);
        }

        public PopulatedMandatoryContext setTaxTotals(TaxTotalList taxTotalLists) {
            ((PopulatedCreditNoteBuilder) builder).taxTotalList = taxTotalLists;
            return this;
        }

        public PopulatedMandatoryContext setMonetaryTotal(MonetaryTotal monetaryTotal) {
            ((PopulatedCreditNoteBuilder) builder).legalMonetaryTotal = monetaryTotal;
            return this;

        }

        public PopulatedMandatoryContext setDocumentNumber(DocumentNumber documentNumber) {
            ((PopulatedCreditNoteBuilder) builder).documentNumber = documentNumber;
            return collectContext();
        }

        public PopulatedMandatoryContext setDiscrepancyReason(CreditNoteDiscrepancyReason reason) {
            ((PopulatedCreditNoteBuilder) builder).discrepancyReason = reason;
            return collectContext();
        }

        @Override
        public PopulatedOptionalContext withinOptionalSection() {
            return new PopulatedOptionalContext((PopulatedCreditNoteBuilder) builder);
        }

        public CreditNote getResult() throws ElisCoreException {
            PopulatedCreditNoteBuilder creditNoteBuilder = (PopulatedCreditNoteBuilder) builder;

            return creditNoteBuilder.getResult();
        }

        @Override
        protected PopulatedMandatoryContext collectContext() {
            return this;
        }

    }

    public static class PopulatedOptionalContext extends AbstractNoteBuilder.OptionalContext<PopulatedMandatoryContext, PopulatedOptionalContext> {

        private PopulatedOptionalContext(PopulatedCreditNoteBuilder builder) {
            super(builder);
        }

        public PopulatedOptionalContext disableValidations() {
            ((PopulatedCreditNoteBuilder) builder).validate = false;
            return collectContext();
        }

        public PopulatedOptionalContext setCUDE(String cude) {
            ((PopulatedCreditNoteBuilder) builder).cude = cude;
            ((PopulatedCreditNoteBuilder) builder).calculateCUDE = false;
            return this;
        }

        public CreditNote getResult() throws ElisCoreException {
            PopulatedCreditNoteBuilder creditNoteBuilder = (PopulatedCreditNoteBuilder) builder;
            return creditNoteBuilder.getResult();
        }

        @Override
        protected PopulatedOptionalContext collectContext() {
            return this;
        }

    }
}
