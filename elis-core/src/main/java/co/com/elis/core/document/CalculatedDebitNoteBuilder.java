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

public class CalculatedDebitNoteBuilder extends AbstractNoteBuilder<CalculatedDebitNoteBuilder.CalculatedMandatoryContext> {

    private DebitNoteDiscrepancyReason discrepancyReason;

    public CalculatedDebitNoteBuilder(Software software) {
        super(software);
    }

    public DebitNote getCalculatedResult() throws ElisCoreException {
        OtherRelatedData otherData = new OtherRelatedData(delivery, paymentDataBuilder.buildOrNull(), exchangeRate, referenceList, additionalNotes);
        TaxTotalList taxTotalList = TaxTotalList.buildTotalList(true, false, itemList.getTaxes());
        MonetaryTotal legalMonetaryTotal = calculateTotals(itemList, taxTotalList, allowanceCharge);
        DocumentNumber documentNumber = new DocumentNumber(prefix, consecutive);

        Discrepancy discrepancy = new Discrepancy(affectedInvoices, discrepancyReason);

        DebitNote debitNote = new DebitNote(
                new Header(supplierParty, software, receiverParty, invoiceDate, documentNumber),
                taxTotalList,
                legalMonetaryTotal,
                itemList,
                otherData,
                discrepancy
        );

        debitNote.validateOrThrow();
        debitNote.calculateCude();
        return debitNote;
    }

    @Override
    public CalculatedMandatoryContext withinMandatorySection() {
        return new CalculatedMandatoryContext(this);
    }

    public CalculatedOptionalContext withinOptionalSection() {
        return new CalculatedOptionalContext(this);
    }

    public static class CalculatedMandatoryContext extends AbstractNoteBuilder.MandatoryContext<CalculatedMandatoryContext, CalculatedOptionalContext> {

        public CalculatedMandatoryContext(CalculatedDebitNoteBuilder invoiceBuilder) {
            super(invoiceBuilder);
        }

        public CalculatedMandatoryContext setDiscrepancyReason(DebitNoteDiscrepancyReason reason) {
            ((CalculatedDebitNoteBuilder) builder).discrepancyReason = reason;
            return collectContext();
        }

        @Override
        public CalculatedOptionalContext withinOptionalSection() {
            return new CalculatedOptionalContext((CalculatedDebitNoteBuilder) builder);
        }

        public DebitNote getCalculatedResult() throws ElisCoreException {
            CalculatedDebitNoteBuilder debitNoteBuilder = (CalculatedDebitNoteBuilder) builder;

            return debitNoteBuilder.getCalculatedResult();
        }

        @Override
        protected CalculatedMandatoryContext collectContext() {
            return this;
        }

    }

    public static class CalculatedOptionalContext extends AbstractNoteBuilder.OptionalContext<CalculatedMandatoryContext, CalculatedOptionalContext> {

        private CalculatedOptionalContext(CalculatedDebitNoteBuilder builder) {
            super(builder);
        }

        public DebitNote getResult() throws ElisCoreException {
            CalculatedDebitNoteBuilder debitNoteBuilder = (CalculatedDebitNoteBuilder) builder;
            return debitNoteBuilder.getCalculatedResult();
        }

        @Override
        protected CalculatedOptionalContext collectContext() {
            return this;
        }

    }
}
