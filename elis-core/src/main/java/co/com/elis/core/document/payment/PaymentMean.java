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
package co.com.elis.core.document.payment;

import co.com.elis.exception.ElisCoreException;
import java.time.LocalDate;
import java.util.Optional;
import lombok.Getter;

public class PaymentMean {

    @Getter
    private PaymentType type;

    @Getter
    private PaymentChannel channel;

    @Getter
    private Optional<LocalDate> dueDate;

    @Getter
    private Optional<String> paymentId;

    private PaymentMean() {
        paymentId = Optional.empty();
        dueDate = Optional.empty();
    }

    public static PaymentMeansBuilder createAs() {
        return new PaymentMeansBuilder();
    }

    public static class PaymentMeansBuilder {

        private final PaymentMean paymentMeans;

        public PaymentMeansBuilder() {
            this.paymentMeans = new PaymentMean();
        }

        public PaymentMeansBuilder withTypeCode(String type) { //TODO: adjust dozer mapping
            paymentMeans.type = PaymentType.valueOfCode(type);
            return this;
        }

        public PaymentMeansBuilder withType(PaymentType type) {
            paymentMeans.type = type;
            return this;
        }

        public PaymentMeansBuilder withChannel(PaymentChannel channel) {
            paymentMeans.channel = channel;
            return this;
        }

        public PaymentMeansBuilder withChannelCode(Integer channel) throws ElisCoreException {
            paymentMeans.channel = PaymentChannel.fromCode(channel).orElseThrow(() -> new ElisCoreException("Invalid mapping for paymentChannel: " + channel));
            return this;
        }

        public PaymentMeansBuilder withDueDate(LocalDate dueDate) {
            paymentMeans.dueDate = Optional.of(dueDate);
            return this;
        }

        public PaymentMeansBuilder withPaymentId(String paymentId) {
            paymentMeans.paymentId = Optional.of(paymentId);
            return this;
        }

        public PaymentMean build() {
            return paymentMeans;
        }

    }

}
