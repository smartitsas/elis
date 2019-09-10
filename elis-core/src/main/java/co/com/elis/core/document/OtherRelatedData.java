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

import co.com.elis.core.document.delivery.Delivery;
import co.com.elis.core.document.payment.PaymentData;
import co.com.elis.core.document.reference.ReferenceList;
import co.com.elis.core.document.validation.ExportationValidationGroup;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class OtherRelatedData {

    @Getter
    private final Delivery delivery;

    @Getter
    @Valid
   // @NotNull(message = "ELIS_CORE_VAL_INV_PAYMENTS")
    private final PaymentData payments;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_EXPORT_EXCHANGE_RATE", groups = ExportationValidationGroup.class)
    private final ExchangeRate exchangeRate;

    @Getter
    private final ReferenceList references;

    @Getter
    private final List<String> additionalNotes;

    public OtherRelatedData(Delivery delivery, PaymentData payments, ExchangeRate exchangeRate, ReferenceList references, List<String> additionalNotes) {
        this.delivery = delivery;
        this.payments = payments;
        this.exchangeRate = exchangeRate;
        this.references = references;
        this.additionalNotes = additionalNotes;
    }

}
