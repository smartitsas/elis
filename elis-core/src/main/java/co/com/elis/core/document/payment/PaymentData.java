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

import co.com.elis.core.person.PaymentReceptor;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class PaymentData {

    @Getter
    private final List<PrepaidPayment> prepaidPayments;

    @Getter
    private final List<PaymentMean> means;

    @Getter
    private final PaymentReceptor paymentReceptor;

    public PaymentData(List<PrepaidPayment> prepaidPayments, List<PaymentMean> means, PaymentReceptor paymentReceptor) {
        this.prepaidPayments = prepaidPayments;
        this.means = means;
        this.paymentReceptor = paymentReceptor;
    }

    public static class PaymentDataBuilder {

        @Getter
        @Setter
        private List<PrepaidPayment> prepaidPayments;

        @Getter
        @Setter
        private List<PaymentMean> means;

        @Getter
        @Setter
        private PaymentReceptor paymentReceptor;

        public PaymentDataBuilder() {
            prepaidPayments = new ArrayList<>();
            means = new ArrayList<>();
        }

        /**
         * Builds a PaymentData if any of its values have been assigned or returns null if none were assigned
         * @return PaymentData with inner data or null if not assigned
         */
        public PaymentData buildOrNull() {
            if (prepaidPayments.isEmpty() && means.isEmpty() && paymentReceptor == null) {
                return null;
            }

            return new PaymentData(prepaidPayments, means, paymentReceptor);
        }

    }

}
