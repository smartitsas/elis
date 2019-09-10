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

import co.com.elis.core.document.builder.AbstractInvoiceBuilder;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.core.software.Software;
import co.com.elis.exception.ElisCoreException;

public class CalculatedInvoiceBuilder extends AbstractInvoiceBuilder<CalculatedInvoiceBuilder.CalculatedMandatoryContext> {

    protected long consecutive;

    public CalculatedInvoiceBuilder(Software software) {
        super(software);
    }

    public Invoice getCalculatedResult() throws ElisCoreException {
        DocumentNumber docNumber = new DocumentNumber(invoicingRange.getPrefix(), consecutive);
        Header header = new Header(supplierParty, software, receiverParty, invoiceDate, docNumber);
        TaxTotalList taxTotalList = TaxTotalList.buildTotalList(true, false, itemList.getTaxes());
        MonetaryTotal legalMonetaryTotal = new MonetaryTotal(currency, itemList.getCalculatedTotal(), taxTotalList.getCalculatedTaxableAmount(), allowanceCharge);

        OtherRelatedData otherData = new OtherRelatedData(delivery, paymentDataBuilder.buildOrNull(), exchangeRate, referenceList, additionalNotes);
        
        Invoice invoice = new Invoice(
                invoiceType,
                header,
                invoicingRange,
                itemList,
                taxTotalList,
                legalMonetaryTotal,
                otherData
        );

        invoice.validateOrThrow();
        invoice.calculateCufe();
        return invoice;
    }

    @Override
    public CalculatedMandatoryContext withinMandatorySection() {
        return new CalculatedMandatoryContext(this);
    }

    public CalculatedOptionalContext withinOptionalSection() {
        return new CalculatedOptionalContext(this);
    }

    public static class CalculatedMandatoryContext extends AbstractInvoiceBuilder.MandatoryContext<CalculatedMandatoryContext, CalculatedOptionalContext> {

        public CalculatedMandatoryContext(CalculatedInvoiceBuilder invoiceBuilder) {
            super(invoiceBuilder);
        }

        public CalculatedMandatoryContext setConsecutive(long consecutive) {
            ((CalculatedInvoiceBuilder) builder).consecutive = consecutive;
            return collectContext();
        }

        @Override
        public CalculatedOptionalContext withinOptionalSection() {
            return new CalculatedOptionalContext((CalculatedInvoiceBuilder) builder);
        }

        public Invoice getCalculatedResult() throws ElisCoreException {
            return ((CalculatedInvoiceBuilder) builder).getCalculatedResult();
        }

        @Override
        protected CalculatedMandatoryContext collectContext() {
            return this;
        }

    }

    public static class CalculatedOptionalContext extends AbstractInvoiceBuilder.OptionalContext<CalculatedMandatoryContext, CalculatedOptionalContext> {

        private CalculatedOptionalContext(CalculatedInvoiceBuilder builder) {
            super(builder);
        }

        public Invoice getCalculatedResult() throws ElisCoreException {
            return ((CalculatedInvoiceBuilder) builder).getCalculatedResult();
        }

        @Override
        protected CalculatedOptionalContext collectContext() {
            return this;
        }

    }
}
