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

import co.com.elis.core.document.ExchangeRate;
import co.com.elis.core.document.DocumentDate;
import co.com.elis.core.document.InvoicingRange;
import co.com.elis.core.document.allowance.AllowanceCharge;
import co.com.elis.core.document.delivery.Delivery;
import co.com.elis.core.document.payment.PaymentData.PaymentDataBuilder;
import co.com.elis.core.document.reference.ReferenceList;
import co.com.elis.core.item.Item;
import co.com.elis.core.item.ItemList;
import co.com.elis.core.person.ReceiverParty;
import co.com.elis.core.person.SupplierParty;
import co.com.elis.core.software.Software;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBuilder<M extends MandatoryBuildContext, I extends Item> {

    protected String currency;
    protected SupplierParty supplierParty;
    protected ReceiverParty receiverParty;
    protected List<AllowanceCharge> allowanceCharge;
    protected DocumentDate invoiceDate;
    protected InvoicingRange invoicingRange;
    protected Delivery delivery;
    protected ExchangeRate exchangeRate;
    protected PaymentDataBuilder paymentDataBuilder;
    protected final ReferenceList referenceList;
    protected final ItemList<I> itemList;
    protected final Software software;
    protected final List<String> additionalNotes;

    public AbstractBuilder(Software software) {
        this.itemList = new ItemList<>();
        this.referenceList = new ReferenceList();
        this.software = software;
        this.additionalNotes = new ArrayList<>();
        this.paymentDataBuilder = new PaymentDataBuilder();
        this.allowanceCharge = new ArrayList<>();
    }

    public abstract M withinMandatorySection();

}
