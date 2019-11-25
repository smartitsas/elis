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

import co.com.elis.core.document.builder.AbstractInvoiceBuilder;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.core.software.Software;
import co.com.elis.exception.ElisCoreException;

public class PopulatedInvoiceBuilder extends AbstractInvoiceBuilder<PopulatedInvoiceBuilder.PopulatedMandatoryContext> {

    private DocumentNumber documentNumber;

    private TaxTotalList taxTotalList;

    private MonetaryTotal legalMonetaryTotal;

    private boolean validate;

    private boolean calculateCUFE;

    private String cufe;

    public PopulatedInvoiceBuilder(Software software) {
        super(software);
        validate = true;
        calculateCUFE = true;
    }

    public Invoice getResult() throws ElisCoreException {
        OtherRelatedData otherData = new OtherRelatedData(delivery, paymentDataBuilder.buildOrNull(), exchangeRate, referenceList, additionalNotes);

        Invoice invoice = new Invoice(
                invoiceType,
                new Header(supplierParty, software, receiverParty, invoiceDate, documentNumber, validityPeriod),
                invoicingRange,
                itemList,
                taxTotalList,
                legalMonetaryTotal,
                otherData
        );
        if (validate) {
            invoice.validateOrThrow();
        }
        if (calculateCUFE) {
            invoice.calculateCufe();
        } else {
            invoice.setCufe(cufe);
        }

        return invoice;
    }

    @Override
    public PopulatedMandatoryContext withinMandatorySection() {
        return new PopulatedMandatoryContext(this);
    }

    public PopulatedOptionalContext withinOptionalSection() {
        return new PopulatedOptionalContext(this);
    }

    public static class PopulatedMandatoryContext extends AbstractInvoiceBuilder.MandatoryContext<PopulatedMandatoryContext, PopulatedOptionalContext> {

        public PopulatedMandatoryContext(PopulatedInvoiceBuilder invoiceBuilder) {
            super(invoiceBuilder);
        }

        public PopulatedMandatoryContext setDocumentNumber(DocumentNumber documentNumber) {
            ((PopulatedInvoiceBuilder) builder).documentNumber = documentNumber;
            return this;
        }

        public PopulatedMandatoryContext setTaxTotals(TaxTotalList taxTotalLists) {
            ((PopulatedInvoiceBuilder) builder).taxTotalList = taxTotalLists;
            return this;
        }

        public PopulatedMandatoryContext setMonetaryTotal(MonetaryTotal monetaryTotal) {
            ((PopulatedInvoiceBuilder) builder).legalMonetaryTotal = monetaryTotal;
            return this;
        }

        @Override
        public PopulatedOptionalContext withinOptionalSection() {
            return new PopulatedOptionalContext((PopulatedInvoiceBuilder) builder);
        }

        public Invoice getResult() throws ElisCoreException {
            return ((PopulatedInvoiceBuilder) builder).getResult();
        }

        @Override
        protected PopulatedMandatoryContext collectContext() {
            return this;
        }

    }

    public static class PopulatedOptionalContext extends AbstractInvoiceBuilder.OptionalContext<PopulatedMandatoryContext, PopulatedOptionalContext> {

        private PopulatedOptionalContext(PopulatedInvoiceBuilder builder) {
            super(builder);
        }

        public PopulatedOptionalContext disableValidations() {
            ((PopulatedInvoiceBuilder) builder).validate = false;
            return collectContext();
        }

        public PopulatedOptionalContext setCUFE(String cufe) {
            ((PopulatedInvoiceBuilder) builder).cufe = cufe;
            ((PopulatedInvoiceBuilder) builder).calculateCUFE = false;
            return this;
        }

        public Invoice getResult() throws ElisCoreException {
            return ((PopulatedInvoiceBuilder) builder).getResult();
        }

        @Override
        protected PopulatedOptionalContext collectContext() {
            return this;
        }

    }

}
