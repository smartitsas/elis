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

package co.com.elis.core.document.payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class PaymentMean {

    @Getter
    private String id;

    @Getter
    private String code;

    @Getter
    private String cardAccount;

    @Getter
    private String creditAccount;

    @Getter
    private LocalDate dueDate;

    @Getter
    private String channelCode;

    @Getter
    private String payerFinancialAccount;

    @Getter
    private String payeeFinancialAccount;

    @Getter
    private final List<String> instructionNotes;

    private PaymentMean() {
        instructionNotes = new ArrayList<>();
    }

    public static PaymentMeansBuilder createAs() {
        return new PaymentMeansBuilder();
    }

    public static class PaymentMeansBuilder {

        private final PaymentMean paymentMeans;

        @Getter
        private final List<String> instructionNotes;

        public PaymentMeansBuilder() {
            this.paymentMeans = new PaymentMean();
            this.instructionNotes = new ArrayList<>();
        }

        public PaymentMeansBuilder withId(String id) {
            paymentMeans.id = id;
            return this;
        }

        public PaymentMeansBuilder withCode(String code) {
            paymentMeans.code = code;
            return this;
        }

        public PaymentMeansBuilder withCardAccount(String cardAccount) {
            paymentMeans.cardAccount = cardAccount;
            return this;
        }

        public PaymentMeansBuilder withCreditAccount(String creditAccount) {
            paymentMeans.creditAccount = creditAccount;
            return this;
        }

        public PaymentMeansBuilder withDueDate(LocalDate dueDate) {
            paymentMeans.dueDate = dueDate;
            return this;
        }

        public PaymentMeansBuilder withPaymentChannelCode(String paymentChannelCode) {
            paymentMeans.channelCode = paymentChannelCode;
            return this;
        }

        public PaymentMeansBuilder withPayerFinancialAccount(String payerFinancialAccount) {
            paymentMeans.payerFinancialAccount = payerFinancialAccount;
            return this;
        }

        public PaymentMeansBuilder withPayeeFinancialAccount(String payeeFinancialAccount) {
            paymentMeans.payeeFinancialAccount = payeeFinancialAccount;
            return this;
        }

        public PaymentMeansBuilder addInstructionNote(String instructionNote) {
            this.instructionNotes.add(instructionNote);
            return this;
        }

        public PaymentMeansBuilder addInstructionNotes(List<String> instructionNotes) {
            this.instructionNotes.addAll(instructionNotes);
            return this;
        }

        public PaymentMean build() {
            paymentMeans.instructionNotes.addAll(instructionNotes);
            return paymentMeans;
        }

    }

}
