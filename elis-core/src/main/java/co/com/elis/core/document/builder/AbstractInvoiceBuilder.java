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
package co.com.elis.core.document.builder;

import co.com.elis.core.document.InvoiceType;
import co.com.elis.core.document.InvoicingRange;
import co.com.elis.core.document.ValidityPeriod;
import co.com.elis.core.document.payment.PaymentMean;
import co.com.elis.core.document.payment.PrepaidPayment;
import co.com.elis.core.item.InvoiceItem;
import co.com.elis.core.person.PaymentReceptor;
import co.com.elis.core.software.Software;
import java.util.List;

public abstract class AbstractInvoiceBuilder<M extends MandatoryBuildContext> extends AbstractBuilder<M, InvoiceItem> {

    protected InvoiceType invoiceType;

    protected ValidityPeriod validityPeriod;

    public AbstractInvoiceBuilder(Software software) {
        super(software);
        this.invoiceType = InvoiceType.SALE;
    }

    public abstract static class MandatoryContext<M extends MandatoryBuildContext, O extends OptionalContext> extends MandatoryBuildContext<M, O, InvoiceItem> {

        public MandatoryContext(AbstractInvoiceBuilder invoiceBuilder) {
            super(invoiceBuilder);
        }

        public M addPaymentMeans(PaymentMean paymentMean) {
            builder.paymentDataBuilder.getMeans().add(paymentMean);
            return collectContext();
        }

        public M addPaymentMeans(List<PaymentMean> paymentMeans) {
            builder.paymentDataBuilder.getMeans().addAll(paymentMeans);
            return collectContext();
        }

        public M setInvoicingRange(InvoicingRange invoicingRange) {
            AbstractInvoiceBuilder castedBuilder = (AbstractInvoiceBuilder) builder;
            castedBuilder.invoicingRange = invoicingRange;
            return collectContext();
        }

    }

    public abstract static class OptionalContext<M extends MandatoryBuildContext, O extends OptionalBuildContext> extends OptionalBuildContext<M, O, InvoiceItem> {

        protected OptionalContext(AbstractInvoiceBuilder<M> builder) {
            super(builder);
        }

        public O setValidityPeriod(ValidityPeriod validityPeriod) {
            AbstractInvoiceBuilder castedBuilder = (AbstractInvoiceBuilder) builder;
            castedBuilder.validityPeriod = validityPeriod;
            return collectContext();
        }

        public O addPrepaidPayment(PrepaidPayment prepaidPayment) {
            builder.paymentDataBuilder.getPrepaidPayments().add(prepaidPayment);
            return collectContext();
        }

        public O setPaymentReceptor(PaymentReceptor paymentReceptor) {
            builder.paymentDataBuilder.setPaymentReceptor(paymentReceptor);
            return collectContext();
        }

        public O setInvoiceType(InvoiceType invoiceType) {
            AbstractInvoiceBuilder castedBuilder = (AbstractInvoiceBuilder) builder;
            castedBuilder.invoiceType = invoiceType;
            return collectContext();
        }

    }
}
