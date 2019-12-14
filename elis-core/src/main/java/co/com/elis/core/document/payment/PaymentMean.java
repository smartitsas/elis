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
import lombok.Getter;

public class PaymentMean {

    @Getter
    private String id;

    @Getter
    private PaymentChannel channel;

    @Getter
    private LocalDate dueDate;

    @Getter
    private String paymentId;

    private PaymentMean() {
    }

    public static PaymentMeansBuilder createAs() {
        return new PaymentMeansBuilder();
    }

    public static class PaymentMeansBuilder {

        private final PaymentMean paymentMeans;

        public PaymentMeansBuilder() {
            this.paymentMeans = new PaymentMean();
        }

        public PaymentMeansBuilder withId(String id) {
            paymentMeans.id = id;
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
            paymentMeans.dueDate = dueDate;
            return this;
        }

        public PaymentMeansBuilder withPaymentId(String paymentId) {
            paymentMeans.paymentId = paymentId;
            return this;
        }

        public PaymentMean build() {
            return paymentMeans;
        }

    }

}
