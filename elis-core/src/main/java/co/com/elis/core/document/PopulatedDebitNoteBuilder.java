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

import co.com.elis.core.item.DebitNoteDiscrepancyReason;
import co.com.elis.core.item.Discrepancy;
import co.com.elis.core.software.Software;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.exception.ElisCoreException;
import java.util.ArrayList;

public class PopulatedDebitNoteBuilder extends AbstractNoteBuilder<PopulatedDebitNoteBuilder.PopulatedMandatoryContext> {

    private TaxTotalList taxTotalList;

    private MonetaryTotal legalMonetaryTotal;

    protected DocumentNumber documentNumber;

    protected boolean validate;

    private DebitNoteDiscrepancyReason discrepancyReason;

    private boolean calculateCUDE;

    private String cude;

    public PopulatedDebitNoteBuilder(Software software) {
        super(software);
        this.affectedInvoices = new ArrayList<>();
        this.validate = true;
    }

    public DebitNote getResult() throws ElisCoreException {
        OtherRelatedData otherData = new OtherRelatedData(delivery, paymentDataBuilder.buildOrNull(), exchangeRate, referenceList, additionalNotes);

        Discrepancy discrepancy = new Discrepancy(affectedInvoices, discrepancyReason);

        DebitNote debitNote = new DebitNote(
                new Header(supplierParty, software, receiverParty, invoiceDate, documentNumber),
                taxTotalList,
                legalMonetaryTotal,
                itemList,
                otherData,
                discrepancy
        );

        if (calculateCUDE) {
            debitNote.calculateCude();
        } else {
            debitNote.setCude(cude);
        }

        if (validate) {
            debitNote.validateOrThrow();
        }
        return debitNote;
    }

    @Override
    public PopulatedMandatoryContext withinMandatorySection() {
        return new PopulatedMandatoryContext(this);
    }

    public PopulatedOptionalContext withinOptionalSection() {
        return new PopulatedOptionalContext(this);
    }

    public static class PopulatedMandatoryContext extends AbstractNoteBuilder.MandatoryContext<PopulatedMandatoryContext, PopulatedOptionalContext> {

        public PopulatedMandatoryContext(PopulatedDebitNoteBuilder invoiceBuilder) {
            super(invoiceBuilder);
        }

        public PopulatedMandatoryContext setTaxTotals(TaxTotalList taxTotalLists) {
            ((PopulatedDebitNoteBuilder) builder).taxTotalList = taxTotalLists;
            return this;
        }

        public PopulatedMandatoryContext setMonetaryTotal(MonetaryTotal monetaryTotal) {
            ((PopulatedDebitNoteBuilder) builder).legalMonetaryTotal = monetaryTotal;
            return this;
        }

        public PopulatedMandatoryContext setDiscrepancyReason(DebitNoteDiscrepancyReason reason) {
            ((PopulatedDebitNoteBuilder) builder).discrepancyReason = reason;
            return collectContext();
        }

        public PopulatedMandatoryContext setDocumentNumber(DocumentNumber documentNumber) {
            ((PopulatedDebitNoteBuilder) builder).documentNumber = documentNumber;
            return collectContext();
        }

        @Override
        public PopulatedOptionalContext withinOptionalSection() {
            return new PopulatedOptionalContext((PopulatedDebitNoteBuilder) builder);
        }

        public DebitNote getResult() throws ElisCoreException {
            PopulatedDebitNoteBuilder creditNoteBuilder = (PopulatedDebitNoteBuilder) builder;

            return creditNoteBuilder.getResult();
        }

        @Override
        protected PopulatedMandatoryContext collectContext() {
            return this;
        }

    }

    public static class PopulatedOptionalContext extends AbstractNoteBuilder.OptionalContext<PopulatedMandatoryContext, PopulatedOptionalContext> {

        private PopulatedOptionalContext(PopulatedDebitNoteBuilder builder) {
            super(builder);
        }

        public PopulatedOptionalContext disableValidations() {
            ((PopulatedDebitNoteBuilder) builder).validate = false;
            return collectContext();
        }

        public PopulatedOptionalContext setCUDE(String cude) {
            ((PopulatedDebitNoteBuilder) builder).cude = cude;
            ((PopulatedDebitNoteBuilder) builder).calculateCUDE = false;
            return this;
        }

        public PopulatedOptionalContext setDiscrepancyReason(DebitNoteDiscrepancyReason reason) {
            ((PopulatedDebitNoteBuilder) builder).discrepancyReason = reason;
            return collectContext();
        }

        public DebitNote getResult() throws ElisCoreException {
            PopulatedDebitNoteBuilder creditNoteBuilder = (PopulatedDebitNoteBuilder) builder;
            return creditNoteBuilder.getResult();
        }

        @Override
        protected PopulatedOptionalContext collectContext() {
            return this;
        }

    }
}
